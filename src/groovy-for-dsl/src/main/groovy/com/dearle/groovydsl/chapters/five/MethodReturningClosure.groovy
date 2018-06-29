package com.dearle.groovydsl.chapters.five

class MethodReturningClosure {
    def member = "first"
    def method (String param ) {
        def local = member

        return {
            "Member: $member Local: $local Parameter: $param"
        }
    }
}


