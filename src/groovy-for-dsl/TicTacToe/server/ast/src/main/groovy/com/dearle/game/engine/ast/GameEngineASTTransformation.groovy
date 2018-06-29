package com.dearle.game.engine.ast

import groovy.transform.CompilationUnitAware
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.ModuleNode
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation

@GroovyASTTransformation (phase = CompilePhase.SEMANTIC_ANALYSIS)
class GameEngineASTTransformation implements ASTTransformation,
        CompilationUnitAware {
    def parser
    def builder
    def compilationUnit

    void visit(ASTNode[] nodes, SourceUnit source) {
        if (!nodes) return
        if (!(nodes[0] instanceof ModuleNode)) return
        if (!source?.name?.endsWith('Engine.groovy')) {
            return
        }
        def gameEngineModel = new GameEngineModel()
        parser = new EnginePatternParser(gameEngineModel, source)
        builder = new EnginePatternBuilder(nodes,
                gameEngineModel, source, compilationUnit)

        for (ClassNode classNode : nodes[0].classes) {
            classNode.visitContents(parser)
        }
        builder.buildEnginePattern()
    }
    @Override
    void setCompilationUnit(CompilationUnit unit) {
        compilationUnit = unit
    }
}
