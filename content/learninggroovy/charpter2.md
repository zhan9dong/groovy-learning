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

* 闭包中内置了很多迭代方法，如，find,findAll,collect 等等。。后面会详细介绍


```groovy

    def list = ['foo', 'bar']
    def newList = []
    list.collect(newList){
        it.toUpperCase()
    }

    println newList
    
    
    //或者
    
     list = ['foo', 'bar']
     newList = []
    list.collect(newList, {
        it.toUpperCase()
    });
    
    println newList

```

* 在groovy中,闭包可以看成是一个 **代码块**，它可以没有参数和返回值；它像java 8以上的lambda语法 或者，像一个有一个方法的java内部类


* 在groovy闭包中的隐含内置变量

- it，当闭包的方法中，没有定义任何参数时，也可以直接用it变量；

- this 跟java中的this一样

- owner 基本上跟this一样，只是除了一种情况，如果该闭包是在其他的闭包中定义的，owner就指向定义它的闭包对象。 如上面最后一种创建上下文：

```groovy
   
    def a = {
        println "a this:" + this
        println "a owner:" + owner // ower指向b
    
    
        def b = {
            println "b this:" + this
            println "b owner:" + owner // ower指向b
        }
        b.call()
    
    
    }
    
    a.call()
    
```
- delegate 基本上是跟owner的一样，除非它通过Closure.setDelegate()

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

* groovy中的switch可以允许专入list,对象，范围...等表达式作为case依据

 如：

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

* 在groovy中，可以使用 metaClass类来，对元对象添加属性和方法

如：对String类，添加一个uppers方法

```groovy
    String.metaClass.uppers = { -> toUpperCase() };
    
    println "aaa".uppers()


    
    
    Integer.metaClass.say = { -> "I am Interger" }
    
    def i = new Integer(100);
    
    println i.say()
    
   
```

## 用@TypeChecked注解进行强类型检查  

```groovy

class Foos{
    int  i = 42.0; //编译通过
}


@TypeChecked
class Foo{
    int  i = 42.0;//编译不通过
}

```

* 在闭包中，如果需要强类型检查，可以像java语法一样定义参数类型

如：

````groovy

   def list = ["a","b","c"]
   list.collect {
    String it -> it.toUpperCase()
   }

````

##Elvis Operator 运算

* 进一步简化三元运算符

```groovy

def a = null;
def b = a?:"bbb";//在定义b变量时，先判断a是否有值，如果有，就取a的值作为b的值，否则就取值"bbb"

println(b)

```

* 安全访问

````groovy

    def person;
    String name = person?.getName();//先判断person是否为不为null,然后，再调用getName方法
    
    println(name)

````
 



