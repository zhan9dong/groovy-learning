package com.dearle.groovydsl.chapters.eight.flip

class LEDFlipSwitch {
    def context = new StateContext()

    def switchOn() {
        context.state.switchOn()
    }

    def switchOff() {
        context.state.switchOff()
    }

    String getState() {
        context.state
    }
}