package com.dearle.game.engine.ast

import groovy.io.FileType

class PlayerService {

    def getPlayers() {
        def players = []

        def dir = new File("engines/tictactoe")
        dir.eachFileRecurse (FileType.FILES) { file ->
            if (file.name.endsWith('Player.groovy')) {
                def className = file.name.lastIndexOf('.').with {it != -1 ? file.name[0..<it] : file.name}
                def playerName = className.replaceAll('[A-Z]') {' ' + it} - ' '
                players << [playerClass: className, name: playerName]
            }
        }
        players
    }

    def getPlayer = { player ->
        def playerInstance
        def playerClassName

        if (player == 'X')
            playerClassName = playerX
        else
            playerClassName = playerO

        def clazz = this.class.classLoader.loadedClasses.find {
            it.name == playerClassName
        }
        if (!clazz) {
            clazz = this.class.classLoader.parseClass(new File("engines/tictactoe/${playerClassName}.groovy"))
        }
        playerInstance = clazz.newInstance()
        playerInstance.player = player

        playerInstance
    }

}
