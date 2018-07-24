# GDK

## grooy中自带集合方法

- sort 对集合进行排序，这个方法，可以接收1个闭包作为参数，或者，无参数


````groovy

    def lst = [13, 12, 15, 14];
    def newlst = lst.sort();//默认排序
    println newlst; //[12, 13, 14, 15]      
    
    //自定义处理
    def newlist2 = lst.sort {
        a, b -> a - b ? -1 : 1
    }
    
    println newlist2; //   [15, 14, 13, 12]
 
````

- findAll 返回所有符合条件的元素。它可以接收1个闭包作为参数，或者，无参数， 

```groovy

    def lst = [13, 12, 15, 14,0,-1];
    def newlst = lst.findAll();//[13, 12, 15, 14, -1]

    println newlst;//


    def newlst2 = lst.findAll {
        value -> value < 13
    };
    println newlst2;//[12, 0, -1, null, null]

```

- collect 返回 一个新的list,它可以接收1个闭包作为参数，或者，无参数，

```groovy

    def lst = [13, 12, 15, 14, 0, -1];
    def newlst = [];
    newlst = lst.collect {
        it * it
    }
    
    println newlst //[169, 144, 225, 196, 0, 1]

```

- inject  一个累积的过程，传入inject方法的'I'作为sum的初始值，在遍历collection的过程中，将处理结果("$sum $elem ")保存到sum中

```groovy

    def list = ["love", "you"]
    def aa = list.inject('I') { sum, elem ->
        "$sum $elem "
    }
    
    println aa


```


- each  普通迭代方法

- eachWithIndex 用法和each一样，唯一的不同是，eachWithIndex的传入的闭包，有两个参数，第一个是值，第二个是索引

如：

````groovy

    def list = ["a", "b", "c"]
    
    
    list.eachWithIndex {
        String v, int index ->
            println entry
            println i
    }


````

- find 返回第一个符合条件的元素，它可以接收1个闭包作为条件参数，或者，无参数，

- findIndexOf 返回指定元素的索引值。它可以接收1个闭包作为条件参数，或者，无参数，

- any 返回boolean值。只要有任意一个符合条件就返回true，它可以接收1个闭包作为条件参数，或者，无参数，

- every 返回boolean值，只有所有符合条件才返回true,它可以接收1个闭包作为条件参数，或者，无参数

- reverse, 它将原list倒序，返回新list。无参数

- first 返回原list的第一个元素，无参数

- last 返回原list的最后一个元素，无参数

- tail  返回一个新list,这个list包含原list(除第一个元素)的所有元素，无参数

- 其它的 groupBy，tokenize, unique，max，min，count，sum 等



```groovy

    def  list = [2,9,4,6,1];
    
    println(list.sort())

```

## 通配符 （*） 可以很方便用来访问集合对象所有属性

把它想像成在linux 查看某文件类型目录时的通配符一样

如：
```bash

    ls *.txt

```

在groovy

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

## IO 操作，变得更简单

* groovy可以简单明了的进行文件读写操作

如：

* 读取一个文件内容

```groovy
    println new File("book.txt").text
    
```
* 写入一个文件内容：

```groovy
     //创建文件，并写入
        new File("book.txt").text = "richard zhisui";
        
```

* 也可以通过字节方式写入

```groovy
   
    //先手动创建data文件
    byte[] data = new File("data").bytes;
    
    
    //把data的数据写入book2.txt
    new File("book2.txt").bytes = data;
    
    //创建 dragons.txt 写入内容后，并读取dragons.txt内容
    new File('dragons.txt').eachLine { line-> println(line)   }


```

* 如果需要用InputStream,Reader,OutputStream,Writer这些对象进行操作。groovy也提供类似的方法



- 输入流InputStream操作

````groovy
    Properties properties = new Properties()
    File propertiesFile = new File('test.properties')
    propertiesFile.withInputStream {
        properties.load(it)
    }
    
    println properties.name
    
    println properties.url
    

````


- Reader操作

````groovy

    def lineNo = 1
    def line = 1;
    new File("haiku.txt").withReader { reader ->
        while ((line = reader.readLine())!=null) {
            println "第${lineNo}行内容: ${line}"
            lineNo++
        }
    }

````

- OutputStream操作

如：文件复制


````groovy

    def srcFile = new File("haiku.txt")
    def targetFile = new File("testOutputStream.txt")
    targetFile.withOutputStream {
        outputStream ->
            srcFile.withInputStream {
                inputStream ->
                    outputStream << inputStream 
            }
    }
    
    
    
 

````

- 写入Writer操作
````groovy

    //写入
    new File('mywithWriter.txt').withWriter('utf-8', {
        writer -> writer.writeLine 'Hello World'
    });
    
    //精简版
    
    new File('mywithWriter.txt') << '''Into the ancient pond
    A frog jumps
    Water’s sound!'''    


````



## 极简的URLs操作

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
    
```

* 史上最简易的抓取网页代码

```groovy
    
    println "http://google.com".toURL().text;
    
```


## Ranges 用 ... 表示 范围， 

在groovy中。范围运算，可以用在循环，switch,字符串截取中。

```html

    1..10 - 包含范围的示例
    1 .. <10 - 独占范围的示例
    'a'..'x' - 范围也可以由字符组成
    10..1 - 范围也可以按降序排列
    'x'..'a' - 范围也可以由字符组成并按降序排列。
    
```


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

- ConfigSlurper工具用于读取配置文件非常的方便

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