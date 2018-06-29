package com.dearle.groovydsl.chapters.eight.transforms

class StateMachineModel {
    def states
    def events

    def StateMachineModel() {
        states = []
        events = [:]
    }

    def getInitialState() {
        states[0]
    }
    def addState( state ) {
        if (!states.contains(state))
            states << state
    }

    def addEvent(event) {
        events[ "$event" ] = [:]
    }

    def addTransition(event, transition, next) {
        events["$event"]["$transition"] = next
    }

    def getEvents() {
       events.keySet()
    }

    def getTransitionForState(event, state) {
        def transition = events["$event"]["_all"]

        if (!transition)
            transition = events["$event"]["$state"]

        transition
    }
}
