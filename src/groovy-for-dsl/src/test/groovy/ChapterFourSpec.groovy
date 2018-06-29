import com.dearle.groovydsl.chapters.four.Customer
import com.dearle.groovydsl.chapters.four.Balance
import com.dearle.groovydsl.chapters.four.Name
import com.dearle.groovydsl.chapters.four.Status
import com.dearle.groovydsl.test.SpockScriptSpecification

class ChapterFourSpec extends SpockScriptSpecification {

    def "Run AccountTest example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterFour/AccountTest.groovy")

        when: "we run the script"
            shell.evaluate script

        then: "the savings account account balance is now 20.00"
            shell.savings.balance == 20.00

        and: "the script output the correct details"
            "Account id 2 owner Aaron Anderson balance is 20.0" == output()
    }

    def "Run AccountSample example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterFour/AccountSample.groovy")

        when: "we run the script"
            shell.evaluate script

        then: "the script output the correct details"
            "Account id 2 owner Aaron Anderson balance is 20.0" == output()
    }


    def "parentheses are optional"() {
        given: "we call println with parentheses"
            println ("Hello, World!")

        and: "we call println without parentheses"
            println "Hello, World!"

        expect: "we expect both to succeed"
"""Hello, World!
Hello, World!""" == output()


    }

    def "parentheses are optional 'Sometimes'!"() {
        given: "we have a closure which returns some text"
            def getHello = { "Hello, World!" }
            def hello = getHello

        when: "we try to println hello"
            println hello

        then: "it prints the reference to the closure rather than the text"
            "Hello, World!" != output()

    }

    def "parentheses are not optional 'Sometimes'!"() {
        given: "we have a closure which returns some text"
            def getHello = { "Hello, World!" }
            def hello = getHello ()

        when: "we try to println hello"
            println hello

        then: "it prints the reference to the closure rather than the text"
            "Hello, World!" == output()

    }

    def "we can chain commands with optional dot notation on methods and parentheses" () {
        given: "we have a script to run"
            def script = new File(scriptFileName)

        when: "we run the script"
            shell.evaluate script

        then: "the output us the same as if we had called say('Hello').to('Fred') or say('Goodbye')['to'].call('Barney')"
"""Hello, Fred!
Goodbye, Barney!""" == output()

        where:
            scriptFileName << ["scripts/ChapterFour/CommandChain.groovy","scripts/ChapterFour/CommandChainClosure.groovy"]
    }

    def "return keyword is optional and return type will be that defined by the method prototype"() {
        when: "calling a method with a typed return"
            def result = returnString("Fred")
        then: "type of returned value is String"
            result instanceof String

        when: "we pass the same function an Integer"
            result = returnString(123)
        then: "the type returned is still String"
            result instanceof String
            result == "123"

        when: "we pass a typed Integer"
            result = returnIntegerCoercedToString(123)
        then: "the type returned is still String"
            result instanceof String
            result == "123"
    }

    def "return type can be dynamic"() {
        when: "calling a method with dynamic return type"
            def result = returnDef("Fred")
        then: "type of returned value is String"
            result instanceof String

        when: "we pass the same function an Integer"
            result = returnDef(123)
        then: "the type returned is Integer"
            result instanceof Integer
            result == 123
    }

    def "void return type means we always get null returned"() {
        when: "calling a method with void return, passing String"
            def result = returnVoid("Fred")
        then: "type of returned value is null"
            !result
            result == null

        when: "calling a method with void return, passing Integer"
            result = returnVoid(123)
        then: "type of returned value is null"
            !result
            result == null
    }

    def "GroovyBeans allow us to use property notation instead of setters and getters"() {
        given: "a Groovy object"
            Customer customer = new Customer()

        when: "we set a value via the Setter"
            customer.setName "Brian Beausang"

        then: "we can use either the getter or property notation to retrieve it"
            "Brian Beausang" == customer.getName()
            customer.getName() == customer.name

        when: "we set a value via property notation"
            customer.name = "Carol Coolidge"

        then: "we can use either the getter or property notation to retrieve it"
            "Carol Coolidge" == customer.name
            customer.getName() == customer.name

    }

    def "numeric literals behave like objects"() {
        expect: "numeric literal is implemented with Numeric class"
            2.0 instanceof BigDecimal
            2 instanceof Integer
            2.00000f instanceof Float
            2l instanceof Long
    }

    def "can include arbitrary Groovy expressions in Strings"() {
        given: "a Groovy object"
            Customer customer = new Customer(name:"Daniel Dewdney")
            def string1 = "Customer name is Daniel Dewdney"
            def string2 = "Customer name is ${customer.name}"

        expect: "We can compare both strings as if the are equivalent"
            string1 == string2

        and: "they are implemented by two different classes"
            string1 instanceof String
            string2 instanceof GString
    }

    def "Groovy allows multiline strings by using tripple quotes"() {
        given: "some multiline strings with single and double quotes"
            String multiLine = '''Line one
                Line two
                "We don't need to escape quotes in a multi-line string"
            '''
            def name = "Daniel Dewdney"
            def customerSelectSQL = """
                select * from customer where name = ('${name}');
            """

        expect:
            multiLine instanceof String
            customerSelectSQL instanceof GString
    }

    def "we can express a regex pattern using several different String formats"() {
        given: "A String we want to match"
            def matchMe = "Match Me"
        expect: "We can do an exact match using single quoted Strings"
            matchMe ==~ 'Match Me'
        and: "using multiline style String"
            matchMe ==~ """Match Me"""
        and: "using slashy String"
            matchMe ==~ /Match Me/
    }

    def "the dollar slashy has different escaping"() {
        given: "a dollar slashy"
            def dollarSlashy = $/
        $ dollar
        $$ dollar
        \ backslash
        / slash
        $/ slash
        /$
        and: "an old style multiline string"
            def multi = """
        \$ dollar
        \$ dollar
        \\ backslash
        / slash
        / slash
        """
        expect:
            multi == dollarSlashy
    }

    def "we can search for matches with the Groovy find =~ operator"() {
        given: "A String with words we want to match"
            def quickBrownFox =
                    "The quick brown fox jumps over the lazy dog."
        and: "a matcher built via the find operator"
            def matcher = quickBrownFox =~ /\b.o.\b/
        expect: "to match all three letter words with middle letter o"
            matcher.findAll() == ['fox','dog']
    }

    def "we can use a pattern object for greater speed and efficiency"() {
        given: "A String with words we want to match"
            def quickBrownFox =
                    "The quick brown fox jumps over the lazy dog."
        and: "a matcher built via a pattern object"
            def pattern = ~/\b.o.\b/
            def matcher = pattern.matcher( quickBrownFox )
        expect: "to match all three letter words with middle letter o"
            matcher.findAll() == ['fox','dog']
    }

    def "Run MethodCall example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterFour/MethodCall.groovy")

        when: "we run the script"
            shell.evaluate script

        then: "the script output the correct details"
            """Hello, World!
Goodbye, World!""" == output()
    }

    def "Run ClassMethodCall example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterFour/ClassMethodCall.groovy")

        when: "we run the script"
            shell.evaluate script

        then: "the script outputs the correct details"
            """Hello, World!
Goodbye, World!""" == output()
    }

    def "a closure can access vaariables in its local scope"() {
        given: "a variable in scope"
            def greeting = "Hello"
        and: "a closure that can access the variable"
            def greet = { println "$greeting, World!"}
        when: "we invoke the closure with different values for the variable"
            greet()
            greeting = "Goodbye"
            greet()
        then: "the output is as expected"
            """Hello, World!
Goodbye, World!""" == output()
    }

    def "we can pass parameters to closures"() {
        given: "a closure which takes a single parameter"
            def greet = { greeting -> println "$greeting, World!"}
        when: "we call closure with a single parameter"
            greet("Hello")
        then: "that parameter was what was passed"
            "Hello, World!" == output()
    }

    def "we can pass multiple parameters to closures"() {
        given: "a closure which takes a several parameters"
            def greet = { String greeting, name ->
                println "$greeting, $name!"
            }
        when: "we call closure with a single parameter"
            greet("Goodbye", "Fred")
        then: "that parameter was what was passed"
            "Goodbye, Fred!" == output()
    }

    def "closures can be passed as method parameters e.g. many collection methods"() {
        given: "a list of fruits"
            def fruits = ["apple","orange","pear"]
        and: "a closure that can operate on a single String"
            def likeIt = {String fruit -> println "I like ${fruit}s"}
        when: "we invoke the each method of list passing the closure"
            fruits.each likeIt
        then: "each element of the list is passed to the closure in turn"
            """I like apples
I like oranges
I like pears""" == output()
    }

    def "Groovy truth: Any initialized value is true"() {
        given: "An initialized value"
            String initialized = "Some Value"
            Customer customer = new Customer(name: "Joey")
            def array = [1,2,3]
            def map = [a:1,b:2]
        expect: "it will evaluate to true"
            initialized
            customer
            array
            map
    }

    def "Groovy truth: Any null/uninitialized value or empty collection is false"() {
        given: "A null uninitialized or empty value"
            String nullString = null
            String uninitializedString
            Customer customer = null
            def array = []
            def map = [:]
            def emptyString = ''
        expect: "it will evaluate to false"
            !nullString
            !uninitializedString
            !customer
            !array
            !map
            !emptyString
    }

    def "A class or enum can implement asBoolean for Groovy Truth"() {
        expect: "Only Status.ACTIVE will return true from asBoolean"
            Status.ACTIVE
            !Status.INACTIVE
            !Status.DELETED
    }
    def "Groovy has a ternary operator (a ? b : c)" () {
        given:
            def b = 'value1'
            def c = 'value2'
        and: "a ternary expression"
            def result1 = (a ? b : c)
        and: "the logical equivalent using if and condition"
            def result2
            if (a) {
                result2 = b
            } else {
                result2 = c
            }
        expect: "these expressions are equivalent for various values of a"
            result1 == result2
        where:
            a << [1,0,2,true,false]
    }

    def "Groovy also has the Elvis operator (a ?: b)" () {
        given:
            def b = 'value1'
        and: "a ternary expression"
            def result1 = (a ?: b)
        and: "the logical equivalent using if and condition"
            def result2
            if (a) {
                result2 = a
            } else {
                result2 = b
            }
        expect: "these expressions are equivalent for various values of a"
            result1 == result2
        where:
            a << [1,0,2,true,false]
    }

    def "Groovy spaceship works like Java's compareTo method"() {
        expect:
            a == (b <=> c)
        and:
            (b <=> c) == b.compareTo(c)
        where:
            a   |   b   |   c
            -1  |   1   |   2
            0   |   1   |   1
            1   |   2   |   1
    }

    def "Spaceship and Elvis operators together make for a great sorting combination"() {
        given: "we have a few customer account objects"
            def accounts = [
                new Balance(balance: 200.00,
                        first:"Fred", last:"Flintstone"),
                new Balance(balance: 100.00,
                        first:"Wilma", last:"Flintstone"),
                new Balance(balance: 100.00,
                        first:"Betty", last:"Rubble"),
                new Balance(balance: 100.00,
                        first:"Barney", last:"Rubble"),
            ]
        when: "we sort these with spaceship Elvis operators"
            accounts.sort { a, b ->
                a.balance <=> b.balance ?:
                    a.last <=> b.last ?: a.first <=> b.first
            }.each { println it }
        then: "the accounts are sorted by balance - last - first"
            """Flintstone, Wilma : 100.00
Rubble, Barney : 100.00
Rubble, Betty : 100.00
Flintstone, Fred : 200.00""" == output()
    }

    def "we can initialize a GroovyBean by passing a map of the properties to the constructor"() {
        given: "we initialize some beans with a Map of values"
            def map = [id: 1, name: "Barney Rubble"]
            def customer1 = new Customer( map )
            def customer2 = new Customer( id: 2, name: "Fred Flintstone" )

        expect: "the bean properties have been set"
            customer1.id == 1
            customer2.id == 2
            customer1.name == "Barney Rubble"
            customer2.name == "Fred Flintstone"
    }

    def "we can define inclusive and exclusive ranges"() {
        given: "an inclusive an exclusive range"
            def inclusive = 1..10
            def exclusive = 'a'..<'e'
        when: "we collect all the possible values of that range"
            def inclusiveValues = inclusive.collect { it }
            def exclusiveValues = exclusive.collect { it }
        then: "result is an inclusive/exclusive list of those values"
            inclusiveValues == [1,2,3,4,5,6,7,8,9,10]
            exclusiveValues == ['a','b','c','d']
    }

    def "range object have to and from properites"() {
        given: "some ranges"
            def numbers = 1..100
            def letters = 'a'..'z'
        expect: "range has to and from properties"
            numbers.from == 1
            numbers.to == 100
            letters.from == 'a'
            letters.to == 'z'
        and: "range is a java.util.List so we can use contains"
            numbers.contains 2
            numbers.contains 5
        and: "we can use the in keyword with ranges"
            5 in numbers
    }

    def "Ranges are just lists with values in sequence"() {
        given: "a range and the list equivalent"
            def numberList = [1,2,3,4,5,6,7,8,9,10]
            def numberRange = 1..10
        expect: "they are equal"
            numberList == numberRange
    }

    def "We can declare lists within lists and contents can be heterogeneous"(){
        given: "a list within a list"
            def multidimensional = [1,3,5,["apple","orange","pear"]]

        expect: "we can add to lists together using the plus operator"
            [1,3,5] + [["apple","orange","pear"]] == multidimensional
        and: "also with the left shift operator"
            [1,3,5] << ["apple","orange","pear"] == multidimensional
        and: "we can Subtract elements with the minus operator"
            multidimensional - [["apple","orange","pear"]] == [1,3,5]
        and: "we can flatten that multi dimensional list"
            multidimensional.flatten() == [1,3,5,"apple","orange","pear"]
    }

    def "Groovy Lists have many useful helper methods"(){
        given: "some Lists"
            def odds = [1,3,5]
            def evens = [2,4,6]
            def animals = ["cat", "dog", "fox", "cow"]
        expect: "we can reverse the order of a list"
            odds.reverse() == [5,3,1]
        and: "can apply a closure to a list to transform it using collect"
            odds.collect { it + 1 } == evens
        and: "we can find in the list using regex"
            animals.grep( ~/.o./ ) == ["dog", "fox", "cow"]
        and: "we can sort a list"
            [5,1,3].sort() == odds
        and: "we can find elements matching an expression"
            animals.find { it == "dog" } == "dog"
    }

    def "We can iterate a list in both directions"() {
        given:
            def list = [1,3,5]
            def number = ''
        when: "we iterate Iterate forwards"
            list.each { number += it }
        then: "the numbers are added to the string in order"
            number == '135'
        when: "we iterate backwards"
            number = ''
            list.reverseEach { number += it }
        then: "the numbers are added in reverse order"
            number == '531'
    }

    def "we can use any and every methods on a List to test a condition across it"() {
        given:
            def list = [1,2,3,5,7,9]
        expect: "any member is even because 2 is even"
            list.any { it % 2 == 0 }
        and: "every member is not even"
            ! list.every { it % 2 == 0 }
    }

    def "we can use array and property style accessors for Maps"() {
        given: "we declare a simple map"
            def fruitPrices = ["apple":20,"orange":25,"pear":30]
        expect: "we can subscript a map with any key value"
            fruitPrices["apple"] == 20
        and: "use the key like it was a property"
            fruitPrices.apple == 20
    }

    def "we can access a Maps elements using get()"() {
        given: "we have keys of type String we can leave out the quotes"
            def fruitPrices = [apple:20,orange:25,pear:30]
        expect: "we can retrieve a value using the get method"
            fruitPrices.get("apple") == 20
        and: "we can supply a default value for items that are not found"
            fruitPrices.get("grape", 5) == 5
    }

    def "we can declare an empty Map"() {
        given: "we can declare a variable that is empty but is a map"
            def empty = [:]
        expect: "it is an empty map"
            empty instanceof Map
            empty.size() == 0
    }

    def "value assignment can be achieved with superscript or property syntax"() {
        given:
            def fruitPrices = [apple:20,orange:25,pear:30]
        when: "assigning a value, it can be done via superscript"
            fruitPrices['apple'] = 21
        then: "the expected value was set"
            fruitPrices['apple'] == 21
        when: "we try the same with a property accessor"
            fruitPrices.apple = 22
        then: "that also works"
            fruitPrices['apple'] == 22
        when: "assign a value to a key that does not exist"
            fruitPrices.grape = 6
        then: "a new item is added to the Map"
            fruitPrices == [apple:22,orange:25,pear:30, grape:6]
    }

    def "Maps support addition of elements via the plus operator"() {
        given:
            def fruit = [apple:20, orange:25 ]
            def veg = [pea:1, carrot:15]
        expect: "we can add these Maps using plus"
            fruit + veg == [apple:20, orange:25, pea:1, carrot:15]

        and: "map  equality is agnostic to order"
            fruit + veg == [ pea:1, carrot:15, apple:20, orange:25]
    }

    def "Map keys don't have to always be strings"() {
        given:
            def squares = [ 1:1, 2:4, 3.0:9]
        expect:
            squares[1] == 1
            squares[2] == 4
            squares[3.0] == 9
    }

    def "we can declare a key using a variable so long as we surround it with parens"() {
        given:
            def apple = 1
            def map = [ apple:"Red", (apple):"Green"]
        expect:
            map[1] == "Green"
            map["apple"] == "Red"
    }

    def "we can use the spread dot operator to access all keys/values of a Map"() {
        given: "a map and two arrays with the same keys and values"
            def map = [a:"apple", o:"orange", p:"pear"]
            def keys = ["a", "o", "p"]
            def values = ["apple", "orange", "pear"]
        expect: "we can use spread dot to access all keys/values"
            map*.key == keys
            map*.value == values
        and: "which is equivalent to using the collect method"
            map.collect { it.key } == keys
            map.collect { it.value } == values
    }

    def "we can use the spread dot operator to invoke the same method across an array"() {
        given: "An array of Name objects"
            def names = [ new Name(name:"Aaron"),
                          new Name(name:"Bruce"),
                          new Name(name:"Carol")]
        when: "we invoke a method via spread dot"
            names*.greet("Hello")
        then: "the method is called in sequence across all the members"
            """Hello, Aaron
Hello, Bruce
Hello, Carol""" == output()
    }

    def "the spread operator explodes collections into their constituent elements"() {
        given: "An array of Name objects"
            def names = [ new Name(name:"Aaron"),
                          new Name(name:"Bruce"),
                          new Name(name:"Carol")]
        and: "a closure that expects three parameters"
            def greetAll = { a, b, c ->
                println "Hello $a, $b and $c"
            }
        when: "we use spread against the names array"
            greetAll(*names.name)
        then: "It explodes the names array into three separate objects"
            "Hello Arron, Bruce and Carol"
    }

    def "we can use overloaded operators on some Groovy classes such as Date"() {
        given:
            def today = new Date()
            def tomorrow = today + 1
            def yesterday = today - 1
        expect:
            today.plus(1) == tomorrow
            tomorrow.minus(1) == today
            today.minus(1) == yesterday
    }

    String returnString(param) {
        param
    }

    String returnIntegerCoercedToString(Integer param) {
        param
    }

    def returnDef(param) {
        param
    }

void returnVoid(param) {
    param
}



}
