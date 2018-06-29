package com.dearle.groovydsl.chapters.eight.tape

class LoadedState {
    def context
    LoadedState(context) {
        this.context = context
    }

    def load() {
    }

    def start() {
        if (context.state.toString() == 'LOADED')
            context.state = new RunningState(context)
    }

    def pause() {
    }

    def stop() {
    }

    def unload() {
        if (context.state.toString() == 'LOADED')
            context.state = new EmptyState(context)
    }

    String toString() {
        "LOADED"
    }

}
