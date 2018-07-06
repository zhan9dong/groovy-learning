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



