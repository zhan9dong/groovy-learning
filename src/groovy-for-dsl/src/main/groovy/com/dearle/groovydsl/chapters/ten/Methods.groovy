package com.dearle.groovydsl.chapters.ten

/**
 * Example methods used in Chapter 10
 */
class Methods {
    static def method1(Map namedParams = [:], Closure closure = {}) {
        println "method1: $namedParams"
        closure.call()
    }
    static def method2(Map namedParams = [:], Closure closure = {}) {
        println "method2: $namedParams"
        closure.call()
    }
    static def method3(Map namedParams = [:], Closure closure = {}) {
        println "method3: $namedParams"
        closure.call()
    }
}
