## GDK

-  *Collections* 自带集合方法

- sort
- findAll

- collect

- inject

- each

- eachWithIndex

- find

- findIndexOf 

- any 

- every

- reverse

- first

- last

- tail

```groovy
def  list = [2,9,4,6,1];

println(list.sort())

```

## 通配符 （*） 可以很方便用来访问集合对象所有属性

```groovy

a*.name.each { println(it)}

``` 

## GPath  像XPath一样，可以轻松的访问多层的集合对象


````groovy
    def listOfMaps = [['a': 11, 'b': 12], ['a': 21, 'b': 22]]
    
    println(listOfMaps.a);// [11, 21]
    
    
    
     listOfMaps = [['a': 11, 'b': 12], ['a': 21, 'b': 22], null]
    
    println(listOfMaps*.a)
    
    assert listOfMaps*.a == listOfMaps.collect { it?.a }
    
    
    assert listOfMaps.a == [11,21]


````

## IO 操作


```groovy
    //创建文件，并写入
    new File("book.txt").text = "richard zhisui";
    
    
    
    
    //先手动创建data文件
    byte[] data = new File("data").bytes;
    
    
    //把data的数据写入book2.txt
    new File("book2.txt").bytes = data;
    
    
    //创建 dragons.txt 写入内容后，并读取dragons.txt内容
    new File('dragons.txt').eachLine { ina-> println(ina)   }


```


## URLs操作

```groovy
    
    //1.数据抓取
    URL url = new URL("http://google.com");
    InputStream input = (InputStream) url.getContent(); 
    
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    
    int n = 0;
    byte[] arr = new byte[1024];
    
    while (-1 != (n = input.read(arr))) 
        out.write(arr, 0, n);
    System.out.println(new String(out.toByteArray()));
    
    
    
    
    //2.史上最简易的抓取网页代码  
    
    println "http://google.com".toURL().text;

```


## Ranges  区间 用 (..)表示


````groovy

    (1..10).each { println(it)}
    
    for (def i in 1..100){
        println(i)
    }
    
    
    
    
    def text = 'learning groovy'
    
    println text[0..4]
    
    println text[0..4, 8..-1]
    
    def list = ['hank', 'john', 'fred']
    
    println list[0..1] //[hank, john]
    
    
    (1..<5).each { println(it) }
    


````




## 工具

- ConfigSlurper特性，用于读取配置文件非常的方便

```groovy
    
    def config = new ConfigSlurper().parse('''
            app.date = new Date()
     app.age = 42
     app {
      name = "Test${this.age}"  
    }
     ''')
    
    
    def properties = config.toProperties()
    
    println(properties."app.date")
    
    println(properties."app.age")
    
    println(properties."app.name")
    
    

```

- Expando  

Expando类是Groovy语言中的一个相当有趣的类，

它的作用类似于GroovyBean类，

但比GroovyBean类更加灵活；

同时，它还更类似于Map类，

但也比Map类更加灵活。

````groovy

   //1.
    def expando = new Expando()
    
    expando.name = { -> "abc" }
    
    expando.say = { String s -> "${expando.name} says: ${s}" }
    
    
    println expando.say('hello')
    
    
    //2.
    def person = new Expando()
    
    person.name = 'Alice'
    
    person.age = 18
    
    
    person.description = {
    
        println """
               ----------description---------
    
                   name: ${delegate.name}
    
                  age:  ${delegate.age}
               ------------------------------
             """
    }
    
    person.description()
    


````
 

## 可以对 ObservableList/Map/Set的变化添加监听    


````groovy

    def list = new ObservableList()
    def printer = { e -> println e.class }
    list.addPropertyChangeListener(printer)
    list.add 'Harry Potter'
    list.add 'Hermione Granger'
    list.remove(0)

````