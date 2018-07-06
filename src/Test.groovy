def totalPrices(prices, selector) {
    int total = 0
    for (int price : prices)
        if (selector(price)) total += price
    total
}

println totalPrices([1,2,3,4,5,6,7,8,9,10]) { it < 10 }
println totalPrices([1,2,3,4,5,6,7,8,9,10,11,12]) { it > 10 }
println totalPrices([1,2,3,4,5]) { true }

