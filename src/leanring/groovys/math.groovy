
BLOCK: { // this is how you define a block of code.
    // this is so I can reuse variable names.
}
MATH: {

    def pi = 3.141592
    
    println pi
    
    println pi.class
    
    def dPI = 3.141592d
    
    println dPI
    println dPI.class
    
    println (pi + dPI)
    println('BigD + double is ' + (pi + dPI).class)
}

TIMES: {
    3.times { println "everything is an object" }
}


