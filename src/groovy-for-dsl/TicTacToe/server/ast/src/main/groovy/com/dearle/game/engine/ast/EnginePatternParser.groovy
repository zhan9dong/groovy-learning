package com.dearle.game.engine.ast

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.messages.SyntaxErrorMessage
import org.codehaus.groovy.syntax.SyntaxException

class EnginePatternParser implements GroovyClassVisitor {
    def model
    def source

    EnginePatternParser(stateMachineModel, sourceUnit) {
        this.model = stateMachineModel
        this.source = sourceUnit
    }

    @Override
    void visitClass(ClassNode node) {
    }

@Override
void visitMethod(MethodNode node) {
    if (node.name == "run") {
        collectPages(node)
        collectEvents(node)
        collectStateVariables(node)
        collectEventMethodBodies(node)
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

def collectStateVariables(node) {
    def collecting = false
    node.code.statements.each { stmnt ->
        if (!collecting && isStateBlock(stmnt))
            collecting = true

        if (collecting && isNonStateBlock(stmnt)) {
            collecting = false
        }

        if (collecting) {
          if (isDeclarationExpression(stmnt)) {
              model.addStateDeclaration stmnt
          } else {
              addError "Declarations only allowed in state block",
                      stmnt, source
          }
        }
    }
}

boolean isStateBlock(Statement stmnt) {
    stmnt instanceof ExpressionStatement &&
            stmnt.statementLabel &&
            stmnt.statementLabel == 'state'
}

boolean isNonStateBlock(Statement stmnt) {
    stmnt instanceof ExpressionStatement &&
            stmnt.expression instanceof ConstantExpression &&
            stmnt.statementLabel &&
            (stmnt.statementLabel == 'page' ||
             stmnt.statementLabel == 'event' ||
             stmnt.statementLabel == 'when')
}

boolean isDeclarationExpression(Statement stmnt) {
    stmnt instanceof ExpressionStatement &&
            stmnt.expression instanceof DeclarationExpression
}

def collectEventMethodBodies(node) {
    def collecting = false
    def event
    node.code.statements.each { stmnt ->
        if (!collecting && isEventBlock(stmnt)) {
            collecting = true
            event = getLabelParam(stmnt)
            return
        }

        if (collecting && isNonEventBlock(stmnt)) {
            collecting = false
        }

        if (collecting && isEventBlock(stmnt)) {
            event = getLabelParam(stmnt)
        }

        if (collecting) {
            model.addEventStatement event, stmnt
        }
    }

}

boolean isEventBlock(Statement stmnt) {
    stmnt instanceof ExpressionStatement &&
            stmnt.expression instanceof ConstantExpression &&
            stmnt.statementLabel &&
            stmnt.statementLabel == 'event'
}

boolean isNonEventBlock(Statement stmnt) {
    stmnt instanceof ExpressionStatement &&
            stmnt.expression instanceof ConstantExpression &&
            stmnt.statementLabel &&
            (stmnt.statementLabel == 'page' ||
             stmnt.statementLabel == 'when' ||
             stmnt.statementLabel == 'state')
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

    def collectPages(MethodNode node) {
        collectLabeledConstantStrings(node, 'page').each { stmnt ->
            model.addPage stmnt.expression.value.toString()
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
}
