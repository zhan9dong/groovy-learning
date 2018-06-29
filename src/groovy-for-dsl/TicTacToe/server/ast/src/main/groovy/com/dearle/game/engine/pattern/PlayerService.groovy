package com.dearle.game.engine.pattern

class PlayerService {

    def getPlayers() {
        [
                [ playerClass: "NextFreeSpacePlayer"],
                [ playerClass: "RandomPlayer"]
        ]
    }

    def getPlayer = { player ->
        def playerInstance
        if (player == 'X')
            playerInstance = Class.forName("com.dearle.game.engine.${playerX}").newInstance()
        else
            playerInstance = Class.forName("com.dearle.game.engine.${playerO}").newInstance()
        playerInstance.player = player

        playerInstance
    }

}
