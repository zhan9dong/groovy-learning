package com.dearle.game.engine.pattern

class WelcomePage extends PlayerService {
    def session

    WelcomePage(context) {
        this.session = context
        setClosureDelegates()
    }

    def game_start = { Object event ->
        players = getPlayers()
        page = new PlayersPage(session)
    }

    def select_players = { Object event ->
    }

    def play_round = { Object event ->
    }

    def game_end = { Object event ->
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
        "welcome"
    }
}
