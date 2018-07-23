## groovy 介绍
````html
   https://baike.baidu.com/item/Groovy/180590
```` 

## 动态类型

定义一个变量以后，可以对这个变量赋与任何类型的值;

如下：

```groovy

    import org.apache.tools.ant.types.resources.selectors.Date

    def v = 0;//定义了数值类型的变量v
    v = new Date();//然后又给它赋与Date类型的值. 
    
    println(v);

```

## 简单明了的list,map类型

可以使用groovy简单明了的定义list和map数据


- 使用"[]"定义list列表，并，对其直接操作

- 使用":"分隔key和value来定义map数据，key不需要用引号引起来，并且能使用key对其直接对map进行读写操作

```groovy

    //list
    def list = [1, 2]
    list.leftShift(3)
    list.push(0)
    
    list.add("abc")
    //groovy中的<<可以对list数据类型的作用添加值，在数字类型时是位运算操作
    list << "<<号";
    
    list += "加上+="
    
    println(list)
    
    list.forEach({ println(it)})
    
    //map
    def map = [a: 2, b: new Date()];
    
    //写入
    map.put("aaaa","bbb")
    
    println(map)
    //写入
    map.a = "a value"
    println(map)

```

## 在groovy世界任何东西都是对象

* 在java中，区分对待基本类型和引用类型的；

* 在groovy中，一切都是对象。一切都可以当成对象来使用；

如：

```groovy
    100.times {println("hi")}

```

## 属性操作变得更容易

* 在groovy中，定义一个javabeans，不再需要写getter,setter方法。读写属性直接用"."号操作即可

````groovy

    class JavaBeans {
        String a
    }
    
    def beans = new JavaBeans();
    
    beans.a = "richard perpertity"
    
    println(beans.a)

````

## GString

* 双引号""号的下字符串

* 可以直接通过 $var 或者 ${var}方式简单明了的嵌入变量

````groovy

    def  a = "aaaaa"
    def b = "bbbbb"
    
    println("a=${a},b=$b")

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

##使用 ？号来给默认值

```groovy

def a = null;
def b = a?:"bbb";//在定义b变量时，先判断a是否有值，如果有，就取a的值作为b的值，否则就取值"bbb"

println(b)

//也可作空值判断

def person;
String name = person?.getName();

println(name)
```

 

