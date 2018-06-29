package com.dearle.groovydsl.chapters.eight.toggle

class OnState {
    def context
    OnState(context) {
        this.context = context
    }

    def toggle() {
        context.state = new OffState(context)
    }

    String toString() {
        "ON"
    }
}
