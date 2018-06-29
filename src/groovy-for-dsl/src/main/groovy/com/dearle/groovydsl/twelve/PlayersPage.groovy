package com.dearle.groovydsl.twelve

class PlayersPage implements PlayerService {
    def session

    PlayersPage(session) {
        this.session = session
        setClosureDelegates()
    }

    def game_start = { Object event ->
    }

    def select_players = { Object event ->
        // DSL Start ----------------------------
        playerX = event.playerX
        playerO = event.playerO
        // DSL End ------------------------------
        page = new RoundXPage(session)
    }

    def play_round = {Object event ->
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
        "players"
    }
}
