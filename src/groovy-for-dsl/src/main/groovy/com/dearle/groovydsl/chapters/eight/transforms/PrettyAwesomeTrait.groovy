package com.dearle.groovydsl.chapters.eight.transforms

trait PrettyAwesomeTrait {
    void prettyPrint() {
        this.properties.sort { it.key }.each {
            if (it.key != 'prettyPrint' && it.key != 'class')
                println it.key + ": " + it.value
        }
    }

}