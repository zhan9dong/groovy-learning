package com.dearle.game.engine

class RandomBlockingPlayer extends Bot {
    def RandomBlockingPlayer() {

    }
    def RandomBlockingPlayer(String name, String player) {
        super(name,player)
    }
    
    def playRound(grid) {
        if (!Grid.isSolved(grid)) {
            if (Grid.canWin(grid, this.player))
                Grid.playWin grid, this.player
            else if (Grid.canBlock(grid, this.player))
                Grid.playBlock grid, this.player
            else
                Grid.playRandomCell grid, this.player
        }
    }
}