package com.dearle.groovydsl.chapters.eight.transforms

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.PropertyNode
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.PropertyExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types

import java.lang.reflect.Modifier

class StatePatternBuilder {
    def model
    ModuleNode moduleNode
    ClassNode classNode
    String className
    ClassNode contextClass
    String contextClassName

    StatePatternBuilder(ASTNode[] nodes, stateMachineModel) {
        this.model = stateMachineModel
        moduleNode = nodes[0]
        classNode = nodes[0].classes[0]
        className = nodes[0].classes[0].nameWithoutPackage
    }

    void buildStatePattern() {
        buildContextClass()
        updateClientClass()
        buildStateClasses()
    }

    void buildContextClass() {
        def contextClassNode = new AstBuilder().buildFromString CompilePhase.SEMANTIC_ANALYSIS, true, """
    class ${className}Context {
    }
"""

        contextClass = contextClassNode[1]
        moduleNode.addClass(contextClassNode[1])
        contextClassName = "${className}Context"

        contextClassNode[1].addProperty(
            new PropertyNode(
                "state",
                Modifier.PUBLIC,
                ClassHelper.DYNAMIC_TYPE,
                ClassHelper.make (contextClassName),
                new ConstructorCallExpression(
                    ClassHelper.make(
                        "${model.initialState}${className}"
                    ),
                    new ArgumentListExpression(
                        new VariableExpression('this')
                    )
                ),
                null,
                null
            )
        )
    }

    void updateClientClass() {
        buildClientContextProperty()
        buildClientStateGetter()
        buildClientEventMethods()
    }

    def buildClientContextProperty() {
        classNode.addProperty(
            new PropertyNode(
                "context",
                Modifier.PUBLIC,
                ClassHelper.make(contextClassName),
                ClassHelper.make(className),
                new ConstructorCallExpression(
                    ClassHelper.make(contextClassName),
                    ArgumentListExpression.EMPTY_ARGUMENTS
                ),
                null,
                null
            )
        )
    }

    def buildClientStateGetter() {
        def callGetContextState = new ExpressionStatement(
            new MethodCallExpression(
                new MethodCallExpression(
                    new VariableExpression("context"),
                    new ConstantExpression("getState"),
                    ArgumentListExpression.EMPTY_ARGUMENTS
                ),
                "toString",
                ArgumentListExpression.EMPTY_ARGUMENTS
            )
        )

        classNode.addMethod(new MethodNode(
            "getState",
            Modifier.PUBLIC,
            null,
            new Parameter[0],
            null,
            callGetContextState
        )
        )
    }

    def buildClientEventMethods() {
        for (event in model.events) {
            def methodStatement = new ExpressionStatement(
                new MethodCallExpression(
                    new MethodCallExpression(
                        new VariableExpression('context'),
                        new ConstantExpression('getState'),
                        ArgumentListExpression.EMPTY_ARGUMENTS
                    ),
                    new ConstantExpression("${event}"),
                    ArgumentListExpression.EMPTY_ARGUMENTS
                )
            )
            def eventMethodNode = new MethodNode(
                "${event}",
                Modifier.PUBLIC,
                null,
                new Parameter[0],
                null,
                methodStatement
            )
            classNode.addMethod(eventMethodNode)
        }
    }

    def buildStateClasses() {
        for (state in model.states) {
            def stateNode = buildStateClass(state)
            for (event in model.events) {
                buildStateClassEventMethod(event, state, stateNode)
            }
            moduleNode.addClass(stateNode[1])
        }

    }

    def buildStateClass(state) {
        def stateClassNode = new AstBuilder().buildFromString CompilePhase.SEMANTIC_ANALYSIS, true, """
    class ${state}${className} {
        def context
        ${state}${className}(context) {
            this.context = context
        }
        String toString() {
           "${state}"
        }
    }
"""
        stateClassNode
    }

    def buildStateClassEventMethod(event, state, stateClassNode) {
        def methodStatement = null
        def nextState = model.getTransitionForState(event, state)
        if (nextState) {
            methodStatement = new ExpressionStatement(
                new BinaryExpression(
                    new PropertyExpression(
                        new VariableExpression("context"),
                        new ConstantExpression("state")
                    ),
                    new Token(Types.EQUALS, "=", -1, -1),
                    new ConstructorCallExpression(
                        ClassHelper.make("$nextState$className"),
                        new ArgumentListExpression(
                            new VariableExpression('context')
                        )
                    ),
                )
            )
        }
        def eventMethodNode = new MethodNode(
            "${event}",
            Modifier.PUBLIC,
            null,
            new Parameter[0],
            null,
            methodStatement
        )
        stateClassNode[1].addMethod(eventMethodNode)
    }

}
