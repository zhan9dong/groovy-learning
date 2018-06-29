package com.dearle.game.engine.pattern

class GameOverPage extends PlayerService {
    def session

    GameOverPage(context) {
        this.session = context
        setClosureDelegates()
    }

    def game_start = { Object event ->
    }

    def select_players = { Object event ->
    }

    def play_round = { Object event ->
    }

    def game_end = { Object event ->
        page = new WelcomePage(session)
    }

    def setClosureDelegates() {
        if (session) {
            game_start.delegate = session
            select_players.delegate = session
            play_round.delegate = session
            play_round.delegate = session
        }
    }

    String toString() {
        "game_over"
    }
}
