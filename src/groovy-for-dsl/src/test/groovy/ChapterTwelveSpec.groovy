import com.dearle.groovydsl.twelve.GameEngineClient
import spock.lang.Ignore
import spock.lang.Specification

class ChapterTwelveSpec extends Specification {

    @Ignore
    def "we can express a game engine as a state like pattern"() {
        given:
            def gameEngine = new GameEngineClient()

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
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.select_players([playerX: 'NextFreeSpacePlayer',playerO: 'NextFreeSpacePlayer'], savedSession)

        then:
            gameEngine.page == 'roundX'

        when:
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.play_round([player: 'X'], savedSession)

        then:
            gameEngine.page == 'roundO'

        when:
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.play_round([player: 'O'], savedSession)
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.play_round([player: 'X'], savedSession)
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.play_round([player: 'O'], savedSession)
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.play_round([player: 'X'], savedSession)
            gameEngine = new GameEngineClient()
            savedSession = gameEngine.play_round([player: 'O'], savedSession)

        then:
            gameEngine.page == 'roundX'

            // X O X
            // O X O
            // Expect next X play to win

        when:
            gameEngine = new GameEngineClient()
            gameEngine.play_round([player: 'X'], savedSession)

        then:
            gameEngine.page == 'game_over'
    }


}
