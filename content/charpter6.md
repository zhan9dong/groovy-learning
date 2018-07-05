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

##

