package com.dearle.groovydsl.chapters.eight.tape

class RunningState {
    def context
    RunningState(context) {
        this.context = context
    }

    def load() {
    }

    def start() {
    }

    def pause() {
        if (context.state.toString() == 'RUNNING')
            context.state = new PausedState(context)
    }

    def stop() {
        if (context.state.toString() == 'RUNNING')
            context.state = new LoadedState(context)
    }

    def unload() {
    }

    String toString() {
        "RUNNING"
    }

}
