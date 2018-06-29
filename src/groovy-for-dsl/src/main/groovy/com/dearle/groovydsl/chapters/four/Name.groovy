package com.dearle.groovydsl.chapters.four

class Name {
    def name
    def greet(greeting) {
        println "$greeting, $name"
    }
}
