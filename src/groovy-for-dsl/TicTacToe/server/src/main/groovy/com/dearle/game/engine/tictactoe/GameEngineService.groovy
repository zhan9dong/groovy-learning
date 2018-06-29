package com.dearle.game.engine.tictactoe

import org.springframework.stereotype.Component;

@Component
class GameEngineService {

    GroovyClassLoader classLoader

    def getEngineInstance(obj, engine) {
        if (!classLoader) {
            classLoader = new GroovyClassLoader(
                    obj.class.classLoader)
        }
        def clazz = classLoader.loadedClasses.find {
            it.name == "${engine}"
        }
        if (!clazz) {
            clazz = classLoader.parseClass(
                    new File("engines/${engine}/Engine.groovy"))
        }

        clazz.newInstance()
    }
}