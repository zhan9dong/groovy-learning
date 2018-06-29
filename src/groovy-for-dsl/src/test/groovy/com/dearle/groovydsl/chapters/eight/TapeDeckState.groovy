package com.dearle.groovydsl.chapters.eight

state: "EMPTY"
state: "LOADED"
state: "RUNNING"
state: "PAUSED"

event: "load"
    when: "EMPTY"
        next = "LOADED"

event: "start"
    when: "LOADED"
        next = "RUNNING"
    when: "PAUSED"
        next = "RUNNING"

event: "pause"
    when: "RUNNING"
        next = "PAUSED"

event: "stop"
    when: "RUNNING"
        next = "LOADED"
    when: "PAUSED"
        next = "LOADED"

event: "unload"
    when: "LOADED"
        next = "EMPTY"