package com.dearle.groovydsl.chapters.eight.tape

class PausedState {
    def context
    PausedState(context) {
        this.context = context
    }

    def load() {
    }

    def start() {
        if (context.state.toString() == 'PAUSED')
            context.state = new RunningState(context)
    }

    def pause() {
    }

    def stop() {
        if (context.state.toString() == 'PAUSED')
            context.state = new LoadedState(context)
    }

    def unload() {
    }

    String toString() {
        "PAUSED"
    }

}
