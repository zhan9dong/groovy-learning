package com.dearle.groovydsl.chapters.seven

class CustomerPrinter {
    static void prettyPrint(Customer self) {
        println "Customer has following properties"
        self.properties.sort { it.key }.each {
            if (it.key != 'prettyPrint' && it.key != 'class')
                println "    " + it.key + ": " + it.value
        }
    }
}

