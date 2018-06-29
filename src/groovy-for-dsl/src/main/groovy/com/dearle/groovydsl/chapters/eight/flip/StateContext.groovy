package com.dearle.groovydsl.chapters.eight.flip

class StateContext {
    def state = new OffState(this)
}
