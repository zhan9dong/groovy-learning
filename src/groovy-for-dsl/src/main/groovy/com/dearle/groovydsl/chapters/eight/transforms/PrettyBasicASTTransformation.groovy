package com.dearle.groovydsl.chapters.eight.transforms

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.MethodCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.ast.stmt.ExpressionStatement
import org.codehaus.groovy.ast.stmt.ReturnStatement
import org.codehaus.groovy.ast.stmt.Statement
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

import java.lang.reflect.Modifier

@GroovyASTTransformation (phase = CompilePhase.SEMANTIC_ANALYSIS)
class PrettyBasicASTTransformation implements ASTTransformation {

    void visit(ASTNode[] nodes, SourceUnit source) {
        if (!nodes) return
        if (!(nodes[0] instanceof AnnotationNode)) return
        if (!nodes[1] && !(nodes[1] instanceof ClassNode)) return
        ClassNode classNode = nodes[1]

        def methodStatement = new ExpressionStatement(
                    new MethodCallExpression(
                        new VariableExpression("this"),
                        new ConstantExpression("println"),
                        new ArgumentListExpression(
                            new ConstantExpression("I'm so pretty. Oh so pretty!")
                        )
                    )
                )

        def methodNode = new MethodNode(
                'prettyPrint',
                Modifier.PUBLIC,
                null,
                new Parameter[0],
                null,
                methodStatement
        )

        classNode.addMethod(methodNode)
    }

}
