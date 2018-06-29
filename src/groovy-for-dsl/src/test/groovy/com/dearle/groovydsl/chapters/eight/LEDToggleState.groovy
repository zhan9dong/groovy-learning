package com.dearle.groovydsl.chapters.eight

state: "OFF"
state: "ON"

event: "toggle"
    when: "ON"
        next = "OFF"
    when: "OFF"
        next = "ON"