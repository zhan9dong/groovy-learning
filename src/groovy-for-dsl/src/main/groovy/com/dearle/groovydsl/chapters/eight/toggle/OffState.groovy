package com.dearle.groovydsl.chapters.eight.toggle

class OffState {
    def context
    OffState(context) {
        this.context = context
    }

    def toggle() {
        context.state = new OnState(context)
    }

    String toString() {
        "OFF"
    }
}
