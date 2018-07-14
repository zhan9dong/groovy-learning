## 函数式编程

```groovy

    def find(list, tester) {
        for (item in list)
            if (tester(item)) return item
    }
    //调用，两种方式都行
    
    println find([3,3,4,1,2,0,1,23232,2],{ it > 1 });
    
    println find([3,3,4,1,2,0,1,23232,2]){ it > 1 }
    
```