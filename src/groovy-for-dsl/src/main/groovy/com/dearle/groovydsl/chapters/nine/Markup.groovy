package com.dearle.groovydsl.chapters.nine

class Markup {
    static markup = {
        customers {
            customer(id:1001) {
                name(firstName:"Fred",
                  surname:"Flintstone")
                address(street:"1 Rock Road",
                  city:"Bedrock")
            }
            customer(id:1002) {
                name(firstName:"Barney",
                  surname:"Rubble")
                address(street:"2 Rock Road",
                  city:"Bedrock")
            }
        }
    }
}
