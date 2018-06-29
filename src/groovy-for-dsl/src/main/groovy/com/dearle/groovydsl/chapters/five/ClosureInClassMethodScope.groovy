package com.dearle.groovydsl.chapters.five

class ClosureInClassMethodScope {
    def separator = ", "
    def greeting ( name ) {
        def salutation = "Hello"

        def greeter = { println "$salutation$separator$name" }

        greeter()
    }
}
