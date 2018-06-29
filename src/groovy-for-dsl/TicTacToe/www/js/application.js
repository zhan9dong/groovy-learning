var session = {
};

$(document).on("pagebeforecreate", ".engine-page", function (event) {
    var id = $(event.target).attr('id');
    $("#"+id+" .ui-content").html(Mustache.render(MustacheTemplates[id], session));
});

// Game Events
function gameStart() {
    event('game_start', {});
}

function selectPlayers() {
    var selectedX = $(".playerx input:checked").data('player');
    var selectedO = $(".playero input:checked").data('player');
    if (selectedX == null)
        selectedX = "RandomPlayer";
    if (selectedO == null)
        selectedO = "RandomPlayer";
    var params = {
        playerX: selectedX,
        playerO: selectedO
    }

    event('select_players', params);
}

function playRound(player) {
    var params = {
        sessionId: session.sessionId,
        player: player
    }
    event('play_round', params);
}

function gameEnd() {
    var params = {
        sessionId: session.sessionId
    }
    event('game_end', params);
}

function event(event, params) {
    var url = "http://localhost:8080/tictactoe/" +
              event +
             "?callback=?";
    if (session.sessionId != null) {
        params.sessionId = session.sessionId;
    }
    $.getJSON(url, params, function(response) {
        session = response;
        if (response.page != "players") {
            $("#" + response.page + " .ui-content").html(
                Mustache.render(
                    MustacheTemplates[response.page], response));
            $("#" + response.page).trigger("pagecreate");
        } else  {
            session.sessionId = ""
        }
        $.mobile.changePage("#" + response.page,
            { transition: "slide" });
    });
}

