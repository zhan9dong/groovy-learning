package com.dearle.groovydsl.chapters.eight.tape

class KrappsLastTape {
    def context = new StateContext()

    def load() {
        context.state.load()
    }

    def start() {
        context.state.start()
    }

    def pause() {
        context.state.pause()
    }

    def stop() {
        context.state.stop()
    }

    def unload() {
        context.state.unload()
    }

    String getState() {
        context.state
    }
}