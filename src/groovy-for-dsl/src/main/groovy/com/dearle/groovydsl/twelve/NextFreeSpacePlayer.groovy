package com.dearle.groovydsl.twelve

class NextFreeSpacePlayer extends Bot {
    def NextFreeSpacePlayer() {

    }
    def NextFreeSpacePlayer(String name, String player) {
        super(name,player)
    }

    def playRound(grid) {
        if (!Grid.isSolved(grid)) {
            Grid.playNextFreeCell grid, this.player
        }
    }
}