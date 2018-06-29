import com.dearle.game.engine.Grid

page: "welcome"
page: "players"
page: "roundX"
page: "roundO"
page: "gameover"

state:
    String playerX
    String playerO
    List players
    String winner
    def grid = [
            ' ', ' ', ' ',
            ' ', ' ', ' ',
            ' ', ' ', ' '
    ]


event: "game_start"
    players = getPlayers()
    page = "players"

event: "select_players"
    playerX = event.playerX
    playerO = event.playerO
    page = "roundX"

event: "play_round"
    def player = getPlayer(event.player)

    player.playRound(grid)

    if (Grid.isSolved(grid)) {
        winner = event.player
        page = "gameover"
    } else {
        if (event.player == 'X') {
            page = "roundO"
        } else {
            page = "roundX"
        }
    }

event: "game_end"
    page = "welcome"