package com.dearle.game.engine


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