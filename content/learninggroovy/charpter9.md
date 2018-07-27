# 函数式编程

### 函数和闭包

```groovy
    
    //函数
    def find(list, tester) {
        for (item in list)
            if (tester(item)) return item
    }
    //调用，两种方式都行，闭包形式传参
    println find([3,3,4,1,2,0,1,23232,2],{ it > 1 });
    
    println find([3,3,4,1,2,0,1,23232,2]){ it > 1 }
    
    

    
```

* 常见的操作集合函数

```groovy

    class Person {
        String name;
        int age
    }
    
    def persons = [
            new Person(name: 'Bob', age: 20),
            new Person(name: 'A', age: 15),
            new Person(name: 'D', age: 10)
    ]
    
    
    println persons.collect { person -> person.name };
    
    println persons.findAll { person -> person.age >= 18 }
    
    println persons.inject(0) {total,p -> total + p.age}
    
    println persons[0..1]
    
    def a = [1, 2, 3]
    def b = [4, 5]
    println a + b
    // Result: [1, 2, 3, 4, 5]
    
```

* @Immutable注解

 java编程中。如果需要设置某不可重写的，可以通过final关键字

如：

```groovy
    public class Centaur {
        final String name
    
        Centaur(String name) {
            this.name = name
        };
    
    }
    
    Centaur c = new Centaur("richard");
    
    println c.name;
    
    c.name = "richard2" // error
    
```

但如果我们想所有的属性都有final修饰过的效果，而不需要对每个属于进行final关键字的修饰。可以通过@Immutable实现

```groovy
    import groovy.transform.Immutable
    
    @Immutable
    public class Dragon {
        String name;
        final int scales;
    }
    
    Dragon sm = new Dragon("richard", 30);
    
    println sm;
    println sm.scales = 31//error
    
```

### 利用groovy的迭代方法进行流畅的操作

在第四章中，我们介绍了很多迭代方法的使用

我们现在利用所学的迭代方法实现流畅的操作


* 批量截取字符串操作的实例

```groovy
    
    def list = ['foo', 'bar']
    def newList = []
    
    list.collect(newList) { it.substring(1) };
    
    println newList // [oo, ar]
    

```

* 通过连贯操作获取最长的名字的实例

```groovy

    import groovy.transform.Immutable
    
    @Immutable
    public class Dragon {
        String name;
        final int scales;
    }
    
    def dragons = [new Dragon('Smaug', 499), new Dragon('Norbert', 488), new Dragon('Richardgong...', 488),new Dragon('Richardgong', 488)];
    
    String longestName = dragons.findAll { it.name != null }.collect {
        it.name
    }.inject("") { n1, n2 -> n1.length() > n2.length() ? n1 : n2 }
    
    println longestName

```

### groovy curry(咖哩) 方法

通过curry方法，可以预计一些参数值，如：

```groovy

    def concat = { x, y -> return x + y }
    
    def burn = concat.curry("burn");
    
    println burn('wood')

```

再如：

```groovy

    def concat = { x, y -> return x + y }
    def burn = concat.curry("burn")
    def inate = concat.curry("inate")
    
    def composition = { f, g, x -> return f(g(x)) }
    
    def burninate = composition.curry(burn, inate)
    
    def trogdor = burninate(' all the people')
    
    println "Trogdor: ${trogdor}"
    
    // Trogdor: burninate all the people

```

### groovy业务处理函数

groovy允许用户像js这些语言一样，在方法中传入一个处理函数去处理相关的业务

如：

````groovy
    
    class A {
        static breathFire(name) { println "Burninating $name!" }
    }
    
    ['the country side', 'all the people'].each(A.&breathFire)
    
    
````

## 递归溢出

在作递归时，容易出现栈溢出

而使用@TailRecursive注解后，它会按排列方式执行，而不是栈方式

```groovy
    
    long sizeOfList(list, counter = 0) {
        if (list.size() == 0) {
            counter
        } else {
            sizeOfList(list.tail(), counter + 1)
        }
    }
    
    println sizeOfList(1..10000)//栈溢出而报错


```


用@TailRecursive注解后

```groovy

    import groovy.transform.TailRecursive
    
    @TailRecursive
    long sizeOfList(list, counter = 0) {
        if (list.size() == 0) {
            counter
        } else {
            sizeOfList(list.tail(), counter + 1)
        }
    }
    
    println sizeOfList(1..10000)
    
```
