package com.dearle.groovydsl.test

import spock.lang.Specification

class SpockGradleSpecification extends Specification {
    def proc = null

    def runGradleTask(String script, String task = '') {
        def command = "gradle -q -b $script $task"
        proc = command?.execute()
        proc?.waitFor()
    }

    protected String output() {
        proc?.in.text.trim()
    }
}
