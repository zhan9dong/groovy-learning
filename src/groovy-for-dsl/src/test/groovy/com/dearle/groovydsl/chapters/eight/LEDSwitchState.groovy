package com.dearle.groovydsl.chapters.eight

state: "OFF"
state: "ON"

event: "switchOn"
        next = "ON"

event: "switchOff"
        next = "OFF"