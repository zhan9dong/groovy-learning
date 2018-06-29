package com.dearle.game.engine.pattern

class PersistableSession {
    def sessionId
    def page

    def PersistableSession() {
        sessionId = UUID.randomUUID().toString()
    }

    def restoreSession(Map sessionData) {
        sessionData.each { sessionVariable ->
            if (this.properties.containsKey(sessionVariable.key)) {
                if (sessionVariable.key == 'page') {
                    page = Class.forName("com.dearle.game.engine.pattern.${sessionData.page.capitalize()}Page").getDeclaredConstructor(Object.class).newInstance(this)
                 } else {
                    this."${sessionVariable.key}" = sessionVariable.value
                }
            }
        }
    }

    Map asMap() {
        Map theMap = this.properties.clone()
        theMap.remove('class')
        theMap['page'] = this.page.toString()

        theMap
    }

}
