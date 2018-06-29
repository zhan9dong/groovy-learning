package com.dearle.groovydsl.chapters.eight.transforms

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation (phase = CompilePhase.SEMANTIC_ANALYSIS)
class StateMachineASTTransformation implements ASTTransformation {

    def parser
    def builder

    void visit(ASTNode[] nodes, SourceUnit source) {
        if (!nodes) return
        if (!(nodes[0] instanceof ModuleNode)) return
        if (!source?.name?.endsWith('State.groovy')) {
            return
        }
        def stateMachineModel = new StateMachineModel()

        parser = new StatePatternParser(stateMachineModel, source)
        builder = new StatePatternBuilder(nodes, stateMachineModel)

        for (ClassNode classNode : nodes[0].classes) {
            classNode.visitContents(parser)
        }

        builder.buildStatePattern()

    }

}
