package com.dearle.groovydsl.chapters.eight.transforms

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ConstructorNode
import org.codehaus.groovy.ast.FieldNode
import org.codehaus.groovy.ast.GroovyClassVisitor
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.messages.SyntaxErrorMessage
import org.codehaus.groovy.syntax.SyntaxException
import org.codehaus.groovy.syntax.Token

class StatePatternParser implements GroovyClassVisitor {
    def model
    def source

    StatePatternParser(stateMachineModel, sourceUnit) {
        this.model = stateMachineModel
        this.source = sourceUnit
    }

    @Override
    void visitClass(ClassNode node) {
    }

    @Override
    void visitMethod(MethodNode node) {
        if (node.name == "run") {
            def event = null
            def when = null
            collectStates(node)
            collectEvents(node)
            handleBaseErrors(node)
            node.code.statements.each { stmnt ->
                def param = getLabelParam(stmnt)

                switch (stmnt.statementLabel) {
                    case 'state':
                        break
                    case 'event':
                        event = param
                        when = null
                        break
                    case 'when':
                        handleWhenErrors(param, event, stmnt)
                        when = param
                        break
                    default:
                        if (event) {
                            handleWhen(stmnt, event, when)
                        } else {
                            addError "Inappropriate syntax.", stmnt, source
                        }
                }
            }
        }
    }

    def getLabelParam(stmnt) {
        if (stmnt instanceof ExpressionStatement &&
            stmnt.expression instanceof ConstantExpression &&
            stmnt.statementLabel
        ) {
            return stmnt.expression.value.toString()
        }
        null
    }

    def handleWhen(statement, event, transition) {
        if (statement instanceof ExpressionStatement &&
            statement.expression instanceof BinaryExpression) {
          BinaryExpression expr = statement.expression
          def var = expr.leftExpression
          Token oper = expr.operation
          def state = expr.rightExpression

          if ( var instanceof VariableExpression &&
               var.accessedVariable.name == 'next' &&
               oper instanceof Token &&
               oper.rootText == "=" &&
               state instanceof ConstantExpression
          ) {
            def next = state.value.toString()
            if (!model.states.contains(next))
              addError "Reference to non existent state",
                      state, source
            if (event && transition) {
              model.addTransition event, transition, next
            } else {
              model.addTransition event, '_all', next
            }
          } else {
              handleBadWhenExpression var, expr, state
          }
        } else {
            addError "when: or next = 'state' is allowed here",
                    statement, source
        }
    }

    @Override
    void visitConstructor(ConstructorNode node) {
    }

    @Override
    void visitField(FieldNode node) {
    }

    @Override
    void visitProperty(PropertyNode node) {
    }

    def collectStates(MethodNode node) {
        collectLabeledConstantStrings(node, 'state').each { stmnt ->
            model.addState stmnt.expression.value.toString()
        }
    }

    def collectEvents(MethodNode node) {
        collectLabeledConstantStrings(node, 'event').each { stmnt ->
            model.addEvent stmnt.expression.value.toString()
        }
    }

    def collectLabeledConstantStrings(MethodNode node, label) {
        node.code.statements.findAll { stmnt ->
            stmnt instanceof ExpressionStatement &&
            stmnt.expression instanceof ConstantExpression &&
            stmnt.statementLabel &&
            stmnt.statementLabel == label
        }
    }

    def addError(String msg, ASTNode expr, SourceUnit source) {
        int line = expr.lineNumber
        int col = expr.columnNumber
        SyntaxException se = new SyntaxException(msg + '\n', line, col)
        SyntaxErrorMessage sem = new SyntaxErrorMessage(se, source)
        source.errorCollector.addErrorAndContinue(sem)
    }

    def handleBaseErrors(node) {
        if (model.states.size() < 2)
            addError "State machine must have at least two states", node, source
        if (!model.events)
            addError "State machine must have at least one event", node, source

    }
    def handleWhenErrors(state, event, statement) {
        if (!state)
            addError "Expected state value after when:", statement, source
        if (state && !model.states.contains(state))
            addError "Cannot transition from a non existant state", statement.expression, source
        if (!event)
            addError "when: must follow event:", statement, source
    }

    def handleBadWhenExpression(next, expr, state) {
        def oper = expr.operation
        if (!next instanceof VariableExpression || next.accessedVariable.name != 'next') {
            addError "assignment to next is only allowed", next, source
        } else if (oper instanceof Token || oper.rootText != "=") {
            addError "assignment operator is only allowed", expr, source
        } else if (!state instanceof ConstantExpression) {
            addError "assigning a valid state only allowed", state, source
        }
    }

}
