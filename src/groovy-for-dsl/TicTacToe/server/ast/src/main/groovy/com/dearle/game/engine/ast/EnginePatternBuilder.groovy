package com.dearle.game.engine.ast

import org.codehaus.groovy.ast.*
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.*
import org.codehaus.groovy.ast.stmt.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.syntax.Types

import java.lang.reflect.Modifier

class EnginePatternBuilder {
    def model
    ModuleNode moduleNode
    ClassNode classNode
    String className
    ClassNode contextClass
    String sessionClassName
    def sourceUnit
    def compilationUnit

    EnginePatternBuilder(ASTNode[] nodes, model, source, comp) {
        this.model = model
        moduleNode = nodes[0]
        classNode = nodes[0].classes[0]
        className = nodes[0].classes[0].nameWithoutPackage
        this.sourceUnit = source
        this.compilationUnit = comp
    }

    void buildEnginePattern() {
        buildSessionClass()
        updateClientClass()
        buildPageClasses()
        removeMethods()
    }

    void buildSessionClass() {
        def sessionClassNode = new AstBuilder().buildFromString CompilePhase.SEMANTIC_ANALYSIS, true, """
    class ${className}Session extends com.dearle.game.engine.ast.PersistableSession{
    }
"""

        contextClass = sessionClassNode[1]
        moduleNode.addClass(sessionClassNode[1])
        sessionClassName = "${className}Session"

        def blockStatement = new BlockStatement([
            new ExpressionStatement(
                new ConstructorCallExpression(
                    ClassHelper.make(
                        PersistableSession.class
                    ),
                    ArgumentListExpression.EMPTY_ARGUMENTS
                )
            ),
            new ExpressionStatement(
                 new BinaryExpression(
                     new VariableExpression("page"),
                     new Token(Types.EQUALS, "=", -1, -1),
                     new ConstructorCallExpression(
                         ClassHelper.make(
                             "${model.startPage}Page"
                         ),
                         new ArgumentListExpression(
                             new VariableExpression('this')
                         )
                     ),
                 )
            )
        ],
        new VariableScope()
        )

        def constructorNode = new ConstructorNode(
                Modifier.PUBLIC,
                [] as Parameter [],
                [
                        ClassHelper.make(Exception, false),
                        ClassHelper.make(IOException, false)
                ] as ClassNode [],
                blockStatement
        )
        sessionClassNode[1].addConstructor(constructorNode)

        // Add Properties for each variable declaration in the DSL
        model.stateDeclarations.each { stmnt ->
            def statePropertyNode = new PropertyNode (
                stmnt.expression.variableExpression.name,
                Modifier.PUBLIC,
                stmnt.expression.variableExpression.type,
                ClassHelper.make("${className}Session"),
                stmnt.expression.rightExpression,
                null,
                null
            )
            sessionClassNode[1].addProperty(statePropertyNode)
        }
    }

void updateClientClass() {
    addGameEngineTraits()
    buildGameEngineConstructor()
    buildClientEventMethods()
}

def addGameEngineTraits() {
    def traitNode = ClassHelper.make(GameEngineTraits)

    if (!classNode.implementsInterface(traitNode)) {
        classNode.addInterface(traitNode)
    }
}

def buildGameEngineConstructor() {
    def constructorStatement = new ExpressionStatement(
        new BinaryExpression(
           new PropertyExpression(
                new VariableExpression("this"),
                new ConstantExpression("session")
           ),
           new Token(Types.EQUALS, "=", -1, -1),
           new VariableExpression("session")
        )
    )
    def constructorNode = new ConstructorNode(
        Modifier.PUBLIC,
        [
            new Parameter(
                    ClassHelper.make(Object, false), "session")
        ] as Parameter [],
        [
            ClassHelper.make(Exception, false),
            ClassHelper.make(IOException, false)
        ] as ClassNode [],
        constructorStatement
    )
    classNode.addConstructor(constructorNode)
}

def buildClientEventMethods() {
    for (event in model.events) {
        def blockStatement = new BlockStatement( [
            new ExpressionStatement(
                new MethodCallExpression(
                    new VariableExpression("this"),
                    new ConstantExpression("restoreSession"),
                    new ArgumentListExpression(
                        [
                            new ConstantExpression(className),
                            new VariableExpression("sessionData")
                        ]
                    )
                )
            ),
            new ExpressionStatement(
                new MethodCallExpression(
                    new MethodCallExpression(
                        new VariableExpression('session'),
                        new ConstantExpression('getPage'),
                        ArgumentListExpression.EMPTY_ARGUMENTS
                    ),
                new ConstantExpression("${event}"),
                    new ArgumentListExpression(
                        new VariableExpression('event')
                    )
                )
            ),
            new ExpressionStatement(
                new MethodCallExpression(
                    new VariableExpression('session'),
                    new ConstantExpression('asMap'),
                    ArgumentListExpression.EMPTY_ARGUMENTS
                )
            )
        ],
        new VariableScope())
        def eventMethodNode = new MethodNode(
            "${event}",
            Modifier.PUBLIC,
            null,
            [
                new Parameter(
                    ClassHelper.make(Object, false),
                    "event",
                    new ConstantExpression(null)
                ),
                new Parameter(
                    ClassHelper.make(Object, false),
                    "sessionData",
                    new ConstantExpression(null)
                )
            ] as Parameter[],
            null,
            blockStatement
        )
        classNode.addMethod(eventMethodNode)
    }
}

    def buildPageClasses() {
        for (page in model.pages) {
            def pageNode = buildPageClass(page)
            for (event in model.events) {
                buildPageClassEventClosure(event, page, pageNode)
            }
            moduleNode.addClass(pageNode[1])
        }

    }

def buildPageClass(page) {
    def pageClassNode = new AstBuilder().buildFromString CompilePhase.SEMANTIC_ANALYSIS, true, """
class ${page}Page extends com.dearle.game.engine.ast.PlayerService{
    def session
    ${page}Page(session) {
        this.session = session
        setClosureDelegates()
    }
    String toString() {
       "${page}"
    }
}
"""
        def closureDelegateStatements = [
            new ExpressionStatement(
                 new BinaryExpression(
                     new PropertyExpression(
                         new VariableExpression("getPlayer"),
                         new ConstantExpression("delegate")
                     ),
                     new Token(Types.EQUALS, "=", -1, -1),
                     new VariableExpression("session")
                 )
             )]
        for (event in model.events) {
             def closureDelegateStatement = new ExpressionStatement(
                     new BinaryExpression(
                             new PropertyExpression(
                                     new VariableExpression("$event"),
                                     new ConstantExpression("delegate")
                             ),
                             new Token(Types.EQUALS, "=", -1, -1),
                             new VariableExpression("session")
                     )
             )
             closureDelegateStatements << closureDelegateStatement
         }

        def blockStatement = new BlockStatement( closureDelegateStatements, new VariableScope() )

        def methodBody = new IfStatement(
                new BooleanExpression (
                        new BinaryExpression(
                                new VariableExpression("session"),
                                new Token(Types.COMPARE_NOT_EQUAL, "!=", -1, -1),
                                new ConstantExpression(null)
                        )
                ),
                blockStatement,
                EmptyStatement.INSTANCE
        )

        def eventMethodNode = new MethodNode(
            "setClosureDelegates",
            Modifier.PUBLIC,
            null,
            [] as Parameter[],
            null,
            methodBody
        )

        pageClassNode[1].addMethod(eventMethodNode)
        pageClassNode
    }

def buildPageClassEventClosure(event, page, stateClassNode) {
    def eventStatements = []
    model.eventStatements["$event"].each {
        eventStatements << it
    }

    def blockStatement = new BlockStatement(
        eventStatements as Statement [],
        new VariableScope()
    )

    PageAssignmentTransformer pageAssignmentTransformer =
            new PageAssignmentTransformer(model, sourceUnit)
    blockStatement.visit(pageAssignmentTransformer)

    def closureExpression = new ClosureExpression(
        [
            new Parameter(
                    ClassHelper.make(Object, false),
                    "event")
        ] as Parameter[],
         blockStatement
    )
    closureExpression.variableScope = new VariableScope()

    def closurePropertyNode = new PropertyNode(
        "${event}",
        Modifier.PUBLIC,
        ClassHelper.DYNAMIC_TYPE,
        ClassHelper.make("${page}Page"),
        closureExpression,
        null,
        null
    )

    stateClassNode[1].addProperty(closurePropertyNode)
}

    def removeMethods() {
        classNode.getMethods("main").each {
            classNode.removeMethod it
        }
    }

}
