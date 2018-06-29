package com.dearle.groovydsl.twelve

trait PlayerService {
    def getPlayers() {
        [
                [ playerClass: "NextFreeSpacePlayer"],
                [ playerClass: "RandomPlayer"]
        ]
    }

    def getPlayer = { player ->
        def playerInstance
        if (player == 'X')
            playerInstance = Class.forName("com.dearle.groovydsl.twelve.${playerX}").newInstance()
        else
            playerInstance = Class.forName("com.dearle.groovydsl.twelve.${playerO}").newInstance()
        playerInstance.player = player

        playerInstance
    }
}
