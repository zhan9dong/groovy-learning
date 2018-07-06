## 默认值

可以对定义的方法参数设置默认值


```groovy

def a(arg = "abc") { println(arg) }


a()

a('hhakakakakkakaksksksks')


```

## Equals, Hashcode ,  更多的简化

可以通过
```groovy
@Canonical

@EqualsAndHashCode

@TupleConstructor
 
@ToString 修饰

```


## 正则表达式。只需要用 ~/表达式/， 匹配时用: 需要匹配的字符=~/正则/;

```groovy

    def email = "909253305@qq.com"
    def isEmail = email == ~/[\w.]+@[\w.]+/
    println(isEmail);
    
    
    email = 'mailto:adam@email.com'
    def mr = email =~ /[\w.]+@[\w.]+/;
    
    if (mr.find()) println mr.group();
    
```
## 连缀操作

```groovy

    class Pie {
        def bake() { this }
    
        def make() { this }
    
        def eat() { this }
    }
    
    new Pie().bake().eat().eat();
    

```

