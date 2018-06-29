package com.dearle.game.engine.ast

import org.codehaus.groovy.ast.ASTNode
import org.codehaus.groovy.ast.ClassHelper
import org.codehaus.groovy.ast.CodeVisitorSupport
import org.codehaus.groovy.ast.expr.ArgumentListExpression
import org.codehaus.groovy.ast.expr.BinaryExpression
import org.codehaus.groovy.ast.expr.ConstantExpression
import org.codehaus.groovy.ast.expr.ConstructorCallExpression
import org.codehaus.groovy.ast.expr.VariableExpression
import org.codehaus.groovy.syntax.Token
import org.codehaus.groovy.control.SourceUnit
import org.codehaus.groovy.control.messages.SyntaxErrorMessage
import org.codehaus.groovy.syntax.SyntaxException

class PageAssignmentTransformer extends CodeVisitorSupport {
    def model
    def source

    PageAssignmentTransformer(model, source) {
        this.model = model
        this.source = source
    }

    @Override
    void visitBinaryExpression(BinaryExpression expr) {
       def var = expr.leftExpression
       Token oper = expr.operation
       def page = expr.rightExpression

       if ( var instanceof VariableExpression &&
            var.accessedVariable.name == 'page' &&
            oper instanceof Token &&
            oper.rootText == "=" &&
            page instanceof ConstantExpression
       ) {
         def next = page.value.toString()
         if (!model.pages.contains(next)) {
             addError "Reference to non existent state",
                     page, source
         } else {
             expr.rightExpression = new ConstructorCallExpression(
                 ClassHelper.make("${next}Page"),
                 new ArgumentListExpression(
                     new VariableExpression('session')
                 )
             )
         }
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
