package com.dearle.game.engine.tictactoe

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*

@RestController
class GameEventController {
    @Autowired
    GameSessionRepository sessionRepo
    @Autowired
    GameEngineService engineService

    @RequestMapping(value = "/{game}/{event}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    Map event(@PathVariable String game,
              @PathVariable String event,
              @RequestParam Map params ) {
        def session = null
        if (params.sessionId)
          session = sessionRepo.findBySessionId(params.sessionId)

        def client = engineService.getEngineInstance(this, game)
        def state = client."${event}"(params,  session?.cache)

        if ( session) {
             session.cache = state
        } else {
             session = new GameSession(
                  sessionId: state.sessionId, cache: state)
        }

        sessionRepo.save( session)
        session.cache
    }
}
