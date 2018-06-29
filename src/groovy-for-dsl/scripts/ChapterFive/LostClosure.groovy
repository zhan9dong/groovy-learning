def flintstones = ["Fred","Barney"]

def greeter = { println "Hello, ${it}" }

flintstones.each( greeter )
greeter "Wilma"

greeter = { }

flintstones.each( greeter )
greeter "Wilma"
