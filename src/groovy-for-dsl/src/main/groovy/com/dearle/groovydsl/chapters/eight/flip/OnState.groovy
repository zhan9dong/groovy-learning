package com.dearle.groovydsl.chapters.eight.flip

class OnState {
    def context
    OnState(context) {
        this.context = context
    }

    def switchOn() {
        if (context.state.toString() == 'OFF')
            context.state = new OnState(context)
    }

    def switchOff() {
        if (context.state.toString() == 'ON')
            context.state = new OffState(context)
    }


    String toString() {
        "ON"
    }

}
