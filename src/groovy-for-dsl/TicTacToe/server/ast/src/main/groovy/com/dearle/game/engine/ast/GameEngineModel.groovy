package com.dearle.game.engine.ast

class GameEngineModel {
    def pages
    def events
    def stateDeclarations
    def eventStatements

    def GameEngineModel() {
        pages = []
        events = [:]
        stateDeclarations = []
        eventStatements = [:]
    }

    def getStartPage() {
        pages[0]
    }
    def addPage( page ) {
        if (!pages.contains(page))
            pages << page
    }

    def addEvent(event) {
        events[ "$event" ] = [:]
    }

    def getEvents() {
       events.keySet()
    }

    def addStateDeclaration(declaration) {
        stateDeclarations << declaration
    }

    def addEventStatement(event, stmnt) {
        if (!eventStatements["$event"])
            eventStatements["$event"] = []

        eventStatements["$event"] << stmnt
    }
}
