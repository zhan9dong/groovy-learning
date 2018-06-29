package com.dearle.groovydsl.chapters.seven

class CustomerWithInvokeMethod {
    int id
    String firstName
    String surname
    String street
    String city

    Object invokeMethod(String name, Object args) {
        if (name == "prettyPrint") {
            println "Customer has following properties"
            this.properties.sort { it.key }.each {
                if (it.key != 'class')
                println "    " + it.key + ": " + it.value
            }
        }
    }
}

