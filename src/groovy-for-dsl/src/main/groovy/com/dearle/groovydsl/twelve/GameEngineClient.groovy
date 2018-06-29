package com.dearle.groovydsl.twelve

class GameEngineClient {
    def session

    def GameEngineClient() {

    }

    def GameEngineClient(session) {
        this.session = session
    }

    Map game_start(Object event = null, Object sessionData = null) {
        restoreSession(sessionData)
        session.page.game_start(event)

        session.asMap()
    }

    Map select_players(Object event = null, Object sessionData = null) {
        restoreSession(sessionData)
        session.page.select_players(event)

        session.asMap()
    }

    Map play_round(Object event = null, Object sessionData = null) {
        restoreSession(sessionData)
        session.page.play_round(event)

        session.asMap()
    }

    Map game_end(Object event = null, Object sessionData = null) {
        restoreSession(sessionData)
        session.page.game_end(event)

        session.asMap()
    }

    def restoreSession(sessionData) {
        if (!session)
            session = new GameSession()
        if(sessionData && sessionData) {
            session.restoreSession(sessionData)
        }
    }

    String getPage() {
        session.page
    }

}