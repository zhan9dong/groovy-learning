package com.dearle.game.engine.ast

import groovy.inspect.swingui.AstNodeToScriptVisitor
import org.codehaus.groovy.ast.ClassNode
import org.codehaus.groovy.classgen.GeneratorContext
import org.codehaus.groovy.control.CompilationFailedException
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.customizers.CompilationCustomizer

class ASTLogCompilationCustomizer extends CompilationCustomizer {
    final PrintStream out

    ASTLogCompilationCustomizer(CompilePhase compilePhase,
                      PrintStream out) {
 		super(compilePhase)
 		this.out = out
	}
	void call(SourceUnit source, GeneratorContext context,
        ClassNode classNode) throws CompilationFailedException {
         StringWriter writer = new StringWriter()
         new AstNodeToScriptVisitor(writer).visitClass(classNode)
         out.println writer
    }
}
