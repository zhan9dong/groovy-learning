#DSL
## 用delegate属性可以直接实现

```groovy
    public class SMS {
        def from(String a) {
            println("from ${a}")
        }
    
        def to(String a) {
            println("to ${a}")
        }
    
        def body(String a) {
            println("body ${a}")
        }
        def static bodyDSL(String a){
            println "bodyDSL ...${a}"
        }
        def send() {
            println("send ...");
        }
        
        def static send(block) {
            def sms = new SMS();
            block.delegate = sms;
            block()
            sms.send()
        }
    }
    
    SMS.send {
        from "richard"
        to " to richard"
        body "hi richard.g"
    }
    
```

groovy的方法调用是可以不用加"()"的，

上一个例子中

```groovy
 SMS.send {
        from "richard"
        to " to richard"
        body "hi richard.g"
    }
    
```
其实是：

```groovy

 SMS.send({
        from "richard"
        to " to richard"
        body "hi richard.g"
    });
```

又如：

````groovy
class SMS {
    def static bodyDSL(String a){
        println "bodyDSL ...${a}"
    }
}

SMS.bodyDSL "hahahahaahah"
````
```groovy

```