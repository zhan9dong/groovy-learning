
BLOCK: { // this is how you define a block of code.
    // this is so I can reuse variable names.
}
STRINGS: {
    // String vs. GStrings

    def hello = 'Hello World!'
    println(hello)
    println(hello.class)
    
    def g = "this is a gstring. $hello"
    println g
    println(g.class)
}