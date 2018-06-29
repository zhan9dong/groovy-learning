package com.dearle.game.engine.tictactoe

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
class GameSession {
    @Id BigInteger id
    String sessionId
    Map cache
}
