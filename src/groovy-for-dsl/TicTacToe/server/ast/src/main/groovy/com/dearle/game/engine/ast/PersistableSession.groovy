package com.dearle.game.engine.ast

class PersistableSession {
    def sessionId
    def page

    def PersistableSession() {
        sessionId = UUID.randomUUID().toString()
    }

    def restoreSession(Map sessionData) {
        def clazz = this.class.classLoader.loadedClasses.find {
            it.name == "${sessionData.page}Page"
        }
        sessionData.each { sessionVariable ->
            if (this.properties.containsKey(sessionVariable.key)) {
                if (sessionVariable.key == 'page') {
                    page = clazz.getDeclaredConstructor(Object.class).newInstance(this)
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
