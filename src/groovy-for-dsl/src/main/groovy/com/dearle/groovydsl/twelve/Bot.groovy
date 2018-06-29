package com.dearle.groovydsl.twelve


class Bot  {
    public String name
    public String player

    def Bot() {

    }
    def Bot(String name, String player) {
        this.name = name
        this.player = player
    }

}