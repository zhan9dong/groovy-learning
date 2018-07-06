## 默认值

可以对定义的方法参数设置默认值


```groovy

    def a(arg = "abc") { println(arg) }
    
    
    a()
    
    a('hhakakakakkakaksksksks')


```

## Equals, Hashcode ,  更多的简化

可以通过
```groovy
    @Canonical
    
    @EqualsAndHashCode
    
    @TupleConstructor
     
    @ToString 修饰

```


## 正则表达式。只需要用 ~/表达式/， 匹配时用: 需要匹配的字符=~/正则/;

```groovy

    def email = "909253305@qq.com"
    def isEmail = email == ~/[\w.]+@[\w.]+/
    println(isEmail);
    
    
    email = 'mailto:adam@email.com'
    def mr = email =~ /[\w.]+@[\w.]+/;
    
    if (mr.find()) println mr.group();
    
```
## 连缀操作

```groovy

    class Pie {
        def bake() { this }
    
        def make() { this }
    
        def eat() { this }
    }
    
    new Pie().bake().eat().eat();
    

```

## 弱化范型

默认情况下groovy的范型是被弱化的

以下运行正常
```groovy
    List<Integer> nums = [1, 2, 3.1415, 'pie']
    
```

除非加上@CompileStatic 加以类型强化

```groovy

    import groovy.transform.CompileStatic
    
    @CompileStatic
    class Foo {
        List<Integer> nums = [1, 2, 3.1415] //error
    }
    
```

## Groovy 数字类型

如果想定义double， float，long类型的数字，只需要在数字后面加上d,f,l


```groovy
    def a = 2d;
    a = 1f;
    a = 2L;

```

## Boolean类型

在默认情况下groovy会把空字符串，0,null地if条件语句下转成false，其它都转成true
 这个特性，有点像javascript语言

````groovy

 if ("foo") println("true")
 if (!"") println("true")
 if (42) println("true")
 if (! 0) println("true")
````

## map 语法糖

groovy应许我们把一个变量当成map的key和value，这个跟es6有点像



````groovy

    def foo = 1
    def bar = 2
    def map = [(foo): bar]
    
    println map  //[1:2]

````

