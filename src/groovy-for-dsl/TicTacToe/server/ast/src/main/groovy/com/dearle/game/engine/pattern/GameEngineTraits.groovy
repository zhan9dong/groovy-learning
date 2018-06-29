package com.dearle.game.engine.pattern

trait GameEngineTraits {
  def session

  def restoreSession(engine, sessionData  = null) {
    if (!session)
      session = Class.forName("com.dearle.game.engine.pattern.${engine}Session").newInstance()
    if(session && sessionData) {
      session.restoreSession(sessionData)
    }
  }

  String getPage() {
    session.page
  }
}
