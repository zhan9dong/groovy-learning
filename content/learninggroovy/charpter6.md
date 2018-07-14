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


## 更简易的元编程

```groovy


```

## Missing Methods

在类里可以定义def methodMissing(String name, args)方法

用于在不实例化一个类时，调用一个不存在的方法是，会自动触发这个方法

如：

```groovy
    class Testss {
        def methodMissing(String name, args) {
            println "kdslkfdsksdfkldsfkldfskl"
        }
    }
    
    def testss = new Testss();
    testss.aa()

```

## 使用@Delegate注解的类 会继承被修饰的类

```groovy
    public class Person {
        def a(){}
    }
    public class b {
     @Delegate final Person person;
    }
```

