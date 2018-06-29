package com.dearle.game.engine

class Grid {
    static def rows = [
        [0,1,2],
        [3,4,5],
        [6,7,8],
        [0,3,6],
        [1,4,7],
        [2,5,8],
        [0,4,8],
        [6,4,2]
    ]
    
    static def show(grid) {
        println '    0   1   2'
        println '  +-----------+  +-----------+'
        println "0 | ${grid[0]} | ${grid[1]} | ${grid[2]} |  | 0 | 1 | 2 |"
        println "  |---+---+---|  |---+---+---|"
        println "1 | ${grid[3]} | ${grid[4]} | ${grid[5]} |  | 3 | 4 | 5 |"
        println "  |---+---+---|  |---+---+---|"
        println "2 | ${grid[6]} | ${grid[7]} | ${grid[8]} |  | 6 | 7 | 8 |"
        println '  +-----------+  +-----------+'
    }
    static void clear(grid) {
        grid = [
        ' ',' ',' ',
        ' ',' ',' ',
        ' ',' ',' '
        ]        
    }
    
    static boolean isSolved(grid) {
        // If the row is not a space the its either X or O
        // If the first cell is the same as second and
        // If the second cell is the same as third then its solved
        rows.find { cells ->
            grid[cells[0]] != ' ' &&
            grid[cells[0]] == grid[cells[1]] &&
            grid[cells[1]] == grid[cells[2]]
        }
    }

    static boolean isComplete(grid) {
        def blank = grid.find { cell ->
            cell == ' '
        }
        !blank
    }
    
    static boolean canWin(grid, player) {
        rows.find { cells ->
            (grid[cells[0]] == player && grid[cells[1]] == player && grid[cells[2]] == ' ') ||
            (grid[cells[0]] == player && grid[cells[2]] == player && grid[cells[1]] == ' ') ||
            (grid[cells[1]] == player && grid[cells[2]] == player && grid[cells[0]] == ' ')
        }
    }
    
    static boolean canBlock(grid, player) {
        def oponent = player == 'X' ? 'O' : 'X'
        rows.find { cells ->
            (grid[cells[0]] == oponent && grid[cells[1]] == oponent && grid[cells[2]] == ' ') ||
            (grid[cells[0]] == oponent && grid[cells[2]] == oponent && grid[cells[1]] == ' ') ||
            (grid[cells[1]] == oponent && grid[cells[2]] == oponent && grid[cells[0]] == ' ')
        }
    }
    
    static boolean canTakeCorner(grid, player) {
        grid[0] == ' ' || grid[2] == ' ' || grid[6] == ' ' || grid[8]
    }
    
    static def playFirstAvailableCorner(grid, player) {
        if (grid[0] == ' ') {
            grid[0] = player
            return
        } else if (grid[2] == ' ' ){
            grid[2] = player
            return
        } else if (grid[6] == ' ') {
            grid[6] = player
            return
        } else if (grid[8] == ' ') {
            grid[8] = player
        }
    }

    static boolean canTakeCenter(grid, player) {
        grid[4] == ' '
    }
    
    static def playCentre(grid, player) {
        grid[4] = player
    }

    static def playRandomCell(grid, player) {
        def random = new Random()
        def x = random.nextInt 10
        while(grid[x] != ' ')
            x = random.nextInt 10
        grid[x] = player
    }
    
    static def playNextFreeCell(grid, player) {
        def x = 0
        while(grid[x] != ' ' && x < 10)
            x = x + 1
        grid[x] = player
    }

    static def playBlock(grid, player) {
        def oponent = player == 'X' ? 'O' : 'X'
        def blockable = rows.find { cells ->
            (grid[cells[0]] == oponent && grid[cells[1]] == oponent && grid[cells[2]] == ' ') ||
            (grid[cells[0]] == oponent && grid[cells[2]] == oponent && grid[cells[1]] == ' ') ||
            (grid[cells[1]] == oponent && grid[cells[2]] == oponent && grid[cells[0]] == ' ')
        }
        if (blockable) {
            def x = blockable.find { index ->
                grid[index] == ' '
            }
            grid[x] = player
        }
    }
    
    static def playWin(grid, player) {
        def winable = rows.find { cells ->
            (grid[cells[0]] == player && grid[cells[1]] == player && grid[cells[2]] == ' ') ||
            (grid[cells[0]] == player && grid[cells[2]] == player && grid[cells[1]] == ' ') ||
            (grid[cells[1]] == player && grid[cells[2]] == player && grid[cells[0]] == ' ')
        }
        if (winable) {
            def x = winable.find { index ->
                grid[index] == ' '
            }
            grid[x] = player
        }
    }
}