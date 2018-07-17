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


##覆盖操作符. 

    groovy可以用重写操作符的英文来覆盖操作符


````groovy

  class Wizards {
  
      def list = []
  
      def leftShift(person) {
          list.add person
      }
  
      def minus(person) {
          list.remove person
      }
  
      String toString() {
          "${list}"
      }
  }
  
  def wiz = new Wizards();
  
  wiz << 'Gandolf'
  
  println wiz
  
  wiz << 'Harry'
  
  println wiz
  
  wiz - 'Harry'
  
  println wiz;
    
  //如一个普通对象直接用运算符操作的话，它会不可控,或者，报错。而我们可以通过覆盖操作操作符实现可控的结果
    
````

##Missing Methods and Properties 留白先？？？？

