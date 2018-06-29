package com.dearle.game.engine.tictactoe

import org.codehaus.groovy.control.CompilerConfiguration
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.ApplicationContext

@EnableAutoConfiguration
@ComponentScan("com.dearle.game.engine.tictactoe")
class Application {
    static void main(String[] args){
        new SpringApplication(Application).run(args)
    }
}
