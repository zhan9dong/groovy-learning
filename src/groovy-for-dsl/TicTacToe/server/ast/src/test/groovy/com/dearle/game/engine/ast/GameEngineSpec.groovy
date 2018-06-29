package com.dearle.game.engine.ast

import com.dearle.game.engine.pattern.*
import org.codehaus.groovy.control.CompilePhase
import org.codehaus.groovy.control.CompilerConfiguration
import spock.lang.Ignore
import spock.lang.Specification

class GameEngineSpec extends Specification {

    static Class engineClass

    def setup() {
    }

    @Ignore
    def "can use an engine parsed at runtime"() {

        given:
            CompilerConfiguration config = new CompilerConfiguration()
            config.addCompilationCustomizers(new ASTLogCompilationCustomizer(CompilePhase.SEMANTIC_ANALYSIS, System.out))
            GroovyClassLoader classLoader = new GroovyClassLoader(this.class.classLoader, config)
            engineClass = classLoader.parseClass(new File("../engines/tictactoe/Engine.groovy"))
            def gameEngine = engineClass.newInstance()

        expect:
            !gameEngine.session

        when:
            def savedSession = gameEngine.game_start()


        then:
            gameEngine.session
            gameEngine.page == 'players'
            gameEngine.session.players.size() == 2
            gameEngine.session.players[0].playerClass == 'NextFreeSpacePlayer'
            gameEngine.session.players[1].playerClass == 'RandomPlayer'

        when:
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.select_players([playerX: 'NextFreeSpacePlayer',playerO: 'NextFreeSpacePlayer'], savedSession)

        then:
            gameEngine.page == 'roundX'

        when:
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.play_round([player: 'X'], savedSession)

        then:
            gameEngine.page == 'roundO'

        when:
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.play_round([player: 'O'], savedSession)
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.play_round([player: 'X'], savedSession)
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.play_round([player: 'O'], savedSession)
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.play_round([player: 'X'], savedSession)
            gameEngine = engineClass.newInstance()
            savedSession = gameEngine.play_round([player: 'O'], savedSession)

        then:
            gameEngine.page == 'roundX'

            // X O X
            // O X O
            // Expect next X play to win

        when:
            gameEngine = engineClass.newInstance()
            gameEngine.play_round([player: 'X'], savedSession)

        then:
            gameEngine.page == 'gameover'
    }

    def "we can express a game engine as a state like pattern"() {
        given:
            def engine = new GameEngineClient()

        expect:
            !engine.session

        when:
            def savedSession = engine.game_start()

        then:
            engine.session
            engine.page == 'players'
            engine.session.players.size() == 2
            engine.session.players[0].playerClass == 'NextFreeSpacePlayer'
            engine.session.players[1].playerClass == 'RandomPlayer'

        when:
            engine = new GameEngineClient()
            savedSession = engine.select_players(
                [playerX: 'NextFreeSpacePlayer',playerO: 'NextFreeSpacePlayer'],
                savedSession)

        then:
            engine.page == 'roundX'

        when:
            engine = new GameEngineClient()
            savedSession = engine.play_round([player: 'X'], savedSession)

        then:
            engine.page == 'roundO'

        when:
            engine = new GameEngineClient()
            savedSession = engine.play_round([player: 'O'], savedSession)
            engine = new GameEngineClient()
            savedSession = engine.play_round([player: 'X'], savedSession)
            engine = new GameEngineClient()
            savedSession = engine.play_round([player: 'O'], savedSession)
            engine = new GameEngineClient()
            savedSession = engine.play_round([player: 'X'], savedSession)
            engine = new GameEngineClient()
            savedSession = engine.play_round([player: 'O'], savedSession)

        then:
            engine.page == 'roundX'

            // X O X
            // O X O
            // Expect next X play to win

        when:
            engine = new GameEngineClient()
            engine.play_round([player: 'X'], savedSession)

        then:
            engine.page == 'game_over'
            }
}
