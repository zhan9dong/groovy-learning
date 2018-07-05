## groovy 介绍
````html
   https://baike.baidu.com/item/Groovy/180590
```` 

## 动态类型

```groovy
import org.apache.tools.ant.types.resources.selectors.Date

def v = 0;
v = new Date();

println(v);

```

## list,map类型

- 使用"[]"定义list列表，并，对其直接操作

- 使用":",定义map数据， 并，对其直接操作

```groovy

def list = [1, 2]
list.leftShift(3)
list.push(0)

list.add("abc")

list << "<<号";

list += "加上+="

println(list)

list.forEach({ println(it)})

def map = [a: 2, b: new Date()];

map.put("aaaa","bbb")

println(map)
map.a = "a value"
println(map)


```

## 任何东西都是对象


```groovy
100.times {println("hi")}

```

## 属性操作变得更容易

````groovy

class JavaBeans {
    String a
}

def beans = new JavaBeans();

beans.a = "a"

println(beans.a)

````

## GString  要求在 "" 下可以实现模版操作

````groovy

    def  a = "aaaaa"
    def b = "bbbbb"
    
    println("a=${a},b=${b}")

````

## 闭包

内置变量
- it （当闭包只有一个参数时变量it就代表这个内置参数变量）

- this 它基本上保持了跟java中this一样的含义（在java的静态方法以及静态域中，this是没有任何含义的）

- owner 它的含义基本上跟this的含义一样，只是除了一种情况，如果该闭包是在其他的闭包中定义的，那么owner是指向定义它的闭包对象。 如上面最后一种创建上下文：

如：
```groovy

    def a = {
        def b = {
            println "closureClosure this:" + this
            println "closureClosure owner:" + owner // ower指向b
            println "closureClosure delegate:" + delegate
        }
        b.call()
    }
    
    a.call()

```
- delegate 义大多数情况下是跟owner的含义一样，除非它被显示的修改（通过Closure.setDelegate()方法进行修改

如

````groovy

    def scriptClosure={  
        println "scriptClosure this:"+this  
        println "scriptClosure owner:"+owner  
        println "scriptClosure delegate:"+delegate  
    }  
    println "before setDelegate()"  
    scriptClosure.call()  
    scriptClosure.setDelegate ("abc")  
    println "after setDelegate()"  
    scriptClosure.call()  

````


## Switch变得更好用

````groovy

    def x = 20;
    switch (x) {
        case [1, 2, 3, 4, 5]:
            println("aaaaaa")
            break;
        case "foo":
            println("bbbbb")
        case 10..1000:
            println("ccccc")
            break;
         case Date:
             println("dddddd")
            break;
    }
    
````


## 元编程
如：对String类，添加一个uppers方法

```groovy
    def metaClass = String.metaClass;
    
    metaClass.uppers = {-> toUpperCase()}
    
    println("aaa".uppers())
   
```

## 用@TypeChecked注解可以用强类型检查  

```groovy

class Foos{
    int  i = 42.0; //编译通过
}


@TypeChecked
class Foo{
    int  i = 42.0;//编译不通过
}

```



