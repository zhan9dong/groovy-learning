package com.dearle.game.engine.pattern

class TicTacToeEngineSession extends PersistableSession {
    def playerX
    def playerO
    def players
    def grid = [
    ' ',' ',' ',
    ' ',' ',' ',
    ' ',' ',' '
    ]

    def TicTacToeEngineSession() {
        super()
        page = new WelcomePage(this)
    }
}
