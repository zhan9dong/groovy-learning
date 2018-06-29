package com.dearle.game.engine.tictactoe

import org.springframework.data.mongodb.repository.MongoRepository

interface GameSessionRepository
        extends MongoRepository<GameSession, BigInteger> {
    GameSession findBySessionId(String sessionId)
}