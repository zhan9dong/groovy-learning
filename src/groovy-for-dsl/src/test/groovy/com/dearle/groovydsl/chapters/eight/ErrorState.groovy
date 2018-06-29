package com.dearle.groovydsl.chapters.eight

// Make changes to this file to see the compilation errors
state: "ON"
state: "OFF"

event: "toggle"
    when: "ON"
        next = "OFF"
