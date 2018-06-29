package com.dearle.groovydsl.chapters.four

enum Status {
    ACTIVE,
    INACTIVE,
    DELETED

    def asBoolean () {
         this == Status.ACTIVE
    }
}
