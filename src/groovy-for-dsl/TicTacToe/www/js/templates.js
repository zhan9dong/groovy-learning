MustacheTemplates = {};

MustacheTemplates.welcome = [
    "<h3>Play Tic Tac Toe</h3>"
].join("\n");

MustacheTemplates.players = [
    "<ul data-role='listview' class='ui-listview'>",
                "<li data-role='list-divider' data-theme='b'>",
                    "Select Player for X",
                    "<span class='ui-li-count today'>{{players.length}}</span>",
                "</li>",
                "<li>",
                    "<fieldset data-role='controlgroup' class='playerx'>",
                        "<legend>Player X:</legend>",
                        "{{#players}}",
                            "<input type='radio' name='player-x' id='{{playerClass}}-x' data-player='{{playerClass}}'>",
                            "<label for='{{playerClass}}-x'>{{name}}</label>",
                        "{{/players}}",
                    "</fieldset>",
                "</li>",
                "<li data-role='list-divider' data-theme='b'>",
                    "Select Player for O",
                    "<span class='ui-li-count today'>{{players.length}}</span>",
                "</li>",
                "<li>",
                    "<fieldset data-role='controlgroup' class='playero'>",
                        "<legend>Player O:</legend>",
                        "{{#players}}",
                            "<input type='radio' name='player-o' id='{{playerClass}}-o' data-player='{{playerClass}}'>",
                            "<label for='{{playerClass}}-o'>{{name}}</label>",
                        "{{/players}}",
                    "</fieldset>",
                "</li>",
    "</ul>"
].join("\n");

MustacheTemplates.roundX = [
    "<div class='grid'>",
        "<h1 class='cell'>{{grid.0}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.1}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.2}}</h1>",
        "<div class='horz-separator'></div>",
        "<h1 class='cell'>{{grid.3}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.4}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.5}}</h1>",
        "<div class='horz-separator'></div>",
        "<h1 class='cell'>{{grid.6}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.7}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.8}}</h1>",
    "</div>"
].join("\n");

MustacheTemplates.roundO = [
    "<div class='grid'>",
        "<h1 class='cell'>{{grid.0}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.1}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.2}}</h1>",
        "<div class='horz-separator'></div>",
        "<h1 class='cell'>{{grid.3}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.4}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.5}}</h1>",
        "<div class='horz-separator'></div>",
        "<h1 class='cell'>{{grid.6}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.7}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell'>{{grid.8}}</h1>",
    "</div>"
].join("\n");

MustacheTemplates.gameover = [
    "<div class='grid {{winner}}-win'>",
        "<h1 class='cell {{grid.0}}'>{{grid.0}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell {{grid.1}}'>{{grid.1}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell {{grid.2}}'>{{grid.2}}</h1>",
        "<div class='horz-separator'></div>",
        "<h1 class='cell {{grid.3}}'>{{grid.3}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell {{grid.4}}'>{{grid.4}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell {{grid.5}}'>{{grid.5}}</h1>",
        "<div class='horz-separator'></div>",
        "<h1 class='cell {{grid.6}}'>{{grid.6}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell {{grid.7}}'>{{grid.7}}</h1>",
        "<div class='vert-separator'></div>",
        "<h1 class='cell {{grid.8}}'>{{grid.8}}</h1>",
    "</div>"
].join("\n");
