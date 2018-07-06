# groovy设计模式

## 策略模式

假设有三个方法
```groovy

    def totalPricesLessThan10(prices) {
        int total = 0
        for (int price : prices)
            if (price < 10) total += price
        total
    }
    
    def totalPricesMoreThan10(prices) {
        int total = 0
        for (int price : prices)
            if (price > 10) total += price
        total
    }
    
    def totalPrices(prices) {
        int total = 0
        for (int price : prices)
            total += price
        total
    }
```
以上三个方法重复很多，并且它们处理模型是一样，我们可以考虑用策略模式重写，


```groovy
    //1. 首先定义处理总的入口
    def totalPrices(prices, selector) {
        int total = 0
        for (int price : prices)
            if (selector(price)) total += price
        total
    }
    
   
    //2.传入不同的策略处理 
    println totalPrices([1,2,3,4,5,6,7,8,9,10]) { it < 10 }
    println totalPrices([1,2,3,4,5,6,7,8,9,10,11,12]) { it > 10 }
    println totalPrices([1,2,3,4,5]) { true }
   
    
```



