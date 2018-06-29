package com.dearle.groovydsl.chapters.seven

class Clazz {
    def method() {
        this
    }

    def methodDelegate() {
        delegate
    }

    def methodOwner() {
        owner
    }
    def closure = {
        [this, delegate, owner]
    }

    def closureWithinMethod() {
        def methodClosure = {
            [this, delegate, owner]
        }
        methodClosure()
    }
}

