package com.dearle.groovydsl.twelve

class RoundXPage implements PlayerService {
    def session

    RoundXPage(context) {
        this.session = context
        setClosureDelegates()
    }

    def game_start = { Object event ->
    }

    def select_players = { Object event ->
    }

    def play_round = { Object event ->
        def player = getPlayer(event.player)

        player.playRound(grid)

        if (Grid.isSolved(grid)) {
            page = new GameOverPage(session)
        } else {
            if (event.player == 'X') {
                page = new RoundOPage(session)
            } else {
                page = new RoundXPage(session)
            }
        }

    }

    def game_end = { Object event ->
    }

    def setClosureDelegates() {
        if (session) {
            game_start.delegate = session
            select_players.delegate = session
            play_round.delegate = session
            play_round.delegate = session

            getPlayer.delegate = session
        }
    }

    String toString() {
        "roundX"
    }
}
