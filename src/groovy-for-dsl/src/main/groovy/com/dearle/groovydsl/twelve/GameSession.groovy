package com.dearle.groovydsl.twelve

class GameSession {
    def sessionId


    //------------------
    def playerX
    def playerO
    def players
    def grid = [
    ' ',' ',' ',
    ' ',' ',' ',
    ' ',' ',' '
    ]
    def page = new WelcomePage(this)

    def GameSession() {
        sessionId = UUID.randomUUID().toString()
    }

    def restoreSession(Map sessionData) {
        this.properties.each {
            println "Properties $it ${it.class}"
        }

        sessionId = sessionData.sessionId
        playerX = sessionData.playerX
        playerO = sessionData.playerO
        grid = sessionData.grid
        switch (sessionData.page) {
            case 'welcome':
                page = new WelcomePage(this)
                break
            case 'players':
                page = new PlayersPage(this)
                break
            case 'roundX':
                page = new RoundXPage(this)
                break
            case 'roundO':
                page = new RoundOPage(this)
                break
            case 'game_over':
                page = new GameOverPage(this)
                break
        }
    }

    Map asMap() {
        [
                sessionId: sessionId,
                playerX: playerX,
                playerO: playerO,
                players: players,
                grid: grid,
                page: page.toString()
        ]
    }
}
