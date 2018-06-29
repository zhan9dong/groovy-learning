package com.dearle.groovydsl.chapters.eight.transforms

import groovy.transform.CompilationUnitAware
import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.AnnotationNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.ast.MethodNode
import org.codehaus.groovy.ast.Parameter
import org.codehaus.groovy.ast.builder.AstBuilder
import org.codehaus.groovy.control.CompilationUnit
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.transform.ASTTransformation
import org.codehaus.groovy.transform.GroovyASTTransformation
import org.codehaus.groovy.transform.trait.TraitComposer

import java.lang.reflect.Modifier

@GroovyASTTransformation (phase = CompilePhase.SEMANTIC_ANALYSIS)
class PrettyAwesomeASTTransformation implements ASTTransformation, CompilationUnitAware {

    def compilationUnit

    void visit(ASTNode[] nodes, SourceUnit source) {
        if (!nodes) return
        if (!(nodes[0] instanceof AnnotationNode)) return
        if (!nodes[1] && !(nodes[1] instanceof ClassNode)) return
        ClassNode classNode = nodes[1]

        def traitNode = ClassHelper.make(PrettyAwesomeTrait)
        if (!classNode.implementsInterface(traitNode)) {
            classNode.addInterface(traitNode)
            TraitComposer.doExtendTraits(classNode,source,compilationUnit)
        }
     }

    @Override
    void setCompilationUnit(CompilationUnit unit) {
        compilationUnit = unit
    }
}
