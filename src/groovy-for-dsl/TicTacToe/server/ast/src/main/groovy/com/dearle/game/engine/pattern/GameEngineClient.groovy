package com.dearle.game.engine.pattern

class GameEngineClient implements GameEngineTraits {
    def GameEngineClient() {
    }
    def GameEngineClient(session) {
        this.session = session
    }
    Map game_start(Object event = null, Object saved = null) {
        restoreSession('TicTacToeEngine', saved)
        session.page.game_start(event)
        session.asMap()
    }
    Map select_players(Object event = null, Object saved = null) {
        restoreSession('TicTacToeEngine', saved)
        session.page.select_players(event)
        session.asMap()
    }
    Map play_round(Object event = null, Object saved = null) {
        restoreSession('TicTacToeEngine', saved)
        session.page.play_round(event)
        session.asMap()
    }
    Map game_end(Object event = null, Object saved = null) {
        restoreSession('TicTacToeEngine', saved)
        session.page.game_end(event)
        session.asMap()
    }
}