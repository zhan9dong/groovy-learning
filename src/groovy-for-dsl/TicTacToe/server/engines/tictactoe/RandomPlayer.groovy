package com.dearle.game.engine


class RandomPlayer extends Bot {
    def RandomPlayer() {

    }
    def RandomPlayer(String name, String player) {
        super(name,player)
    }
    
    def playRound(grid) {
        if (!Grid.isSolved(grid)) {
            Grid.playRandomCell grid, this.player
        }
    }
}