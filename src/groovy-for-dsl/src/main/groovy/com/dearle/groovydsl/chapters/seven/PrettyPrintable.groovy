package com.dearle.groovydsl.chapters.seven

trait PrettyPrintable {
    void prettyPrint() {
        this.properties.sort { it.key }.each {
            if (it.key != 'prettyPrint' && it.key != 'class')
                println it.key + ": " + it.value
        }
    }

}
