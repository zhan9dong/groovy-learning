package com.dearle.game.engine.ast

trait GameEngineTraits {
    def session

    def restoreSession(engine, sessionData  = null) {
        def clazz = this.class.classLoader.loadedClasses.find {
            it.name == "${engine}Session"
        }
        if (!session)
            session = clazz.newInstance()
        if(session && sessionData) {
            session.restoreSession(sessionData)
        }
    }

    String getPage() {
        session.page
    }
}
