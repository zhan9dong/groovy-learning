## 函数式编程

* 函数和闭包

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

* @Immutable注解

```groovy
    import groovy.transform.Immutable
    
    
    @Immutable
    public class A{
        String name;
        int size;
    }
    
    A a = new A("richard", 100);
    
    println a;
```