def factorial
factorial = { BigDecimal n ->
    println "Called"
    if (n < 2)
        1
    else
        n * factorial(n - 1)
}
// will eventually stack error
factorial(1)
factorial(10)
factorial(100)
factorial(1000)
factorial(100000)
factorial(1000000)
factorial(10000000)
factorial(100000000)