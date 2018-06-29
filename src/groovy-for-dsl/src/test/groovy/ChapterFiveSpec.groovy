import com.dearle.groovydsl.java.*
import com.dearle.groovydsl.chapters.five.*
import com.dearle.groovydsl.test.SpockScriptSpecification
import org.codehaus.groovy.runtime.metaclass.MissingMethodExceptionNoStack

class ChapterFiveSpec extends SpockScriptSpecification  {

    def "we can lose a reference to a Closure" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterFive/LostClosure.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """Hello, Fred
Hello, Barney
Hello, Wilma""" == output()
    }

    def "Closures are not executed until we call() them explicitly" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterFive/ClosureOrder.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """one
three
two
four""" == output()
    }

    def "we can iterate an object with the each method"() {
        given: "an Integer"
            def number = 1
        when: "we call the each method on it"
            number.each { println it }
        then: "just the object itself gets passed into the Closure"
            "1" == output()
    }

    def "we can iterate a String with the each method"() {
        given: "a String"
            def string = "String"
        when: "we call the each method on the String"
            string.each { println it }
        then: "each knows to iterate the chars in the String"
            """S
t
r
i
n
g""" == output()
    }

    def "calling a method which accepts multiple params and a Closure"() {
        given:
            def flintstones = ["Fred", "Barney", "Wilma"]
        when: "we call findIndexOf passing int and a Closure"
            def result = flintstones.findIndexOf(0) { it == 'Wilma'}
        then:
            result == 2
    }

    def "can invoke method which accepts a single Closure parameter"() {
        when: "we invoke a method that accepts a closure"
            closureMethod {
                println "Closure called"
            }
        then: "the Closure passed in was executed"
            "Closure called" == output()
    }

    def "can invoke method which accepts a Closure with other parameters"() {
        when: "we invoke a method that accepts a String and a Closure"
            closureMethodInteger(1) {
                println "Line 2"
            }
        then: "the Closure passed in was executed with the parameter"
            """Line 1
Line 2""" == output()
    }

    def "it can be useful to pass parameters on to the Closure"() {
        when: "we invoke a method that accepts a String and a Closure"
            closureMethodString("Dolly") { name ->
                println "Hello, $name"
            }
        then: "the Closure passed in was executed with the parameter"
            """Greet someone
Hello, Dolly""" == output()
    }

    def "we can write a mini DSL with just closures" () {
         given: "we have a script to run"
             def script = new File("scripts/ChapterFive/TransactionLock.groovy")

         when: "we run the script"
             shell.evaluate script

         then:
             """Transaction lock
Customer name was Fred
Customer name is now Barney
Transaction release""" == output()
     }

    def "closure can have zero to many typed or dynamic parameters"() {
        given: "Closures with various parameter definitione"
            def defaultParams = { println it; }
            def dynamicParams = { something -> println something; }
            def intParams = { int something -> println something; }
            def stringParams = { String something -> println something; }
            def noParams = { -> }
        when: "Invoking these with valid parameters"
            defaultParams 1
            defaultParams "String"
            dynamicParams 1
            dynamicParams "String"
            intParams 1
            stringParams "String"
            noParams ()
        then: "we expect them to work"
            notThrown Exception
        when: "we pass an incorrect type for a typed parameter"
            stringParams 1
        then: "we expect that Groovy won't find a matching Closure"
            thrown MissingMethodException
        when: "we pass too many parameters"
            dynamicParams "String1", 1
        then: "that should fail also"
            thrown MissingMethodException
        when: "passing a parameter to a Closure that does not expect one"
            noParams "String"
        then: "we expect that to fail"
            thrown MissingMethodException
    }

    def "can implement a Closure in pure Java"() {
        given: "an instance of a Java Closure"
            def stringParams = new StringClosure(this)
        when: "Invoking this with the parameters defined by the doCall"
            stringParams "String"
        then: "we expect them to work"
            notThrown Exception
        when: "we pass an incorrect type for the doCall parameter"
            stringParams 1
        then: "we expect that Groovy won't find a matching Closure"
            thrown MissingMethodException
    }

    def "can implement multiple doCalls for a Closure in pure Java"() {
        given: "an instance of a Java Closure"
            def multiParams = new MultiClosure(this)
        when: "Invoking these with the parameters defined by the doCall"
            multiParams "String"
        then: "we expect them to work"
            notThrown Exception
        when: "Invoking these with the parameters defined by the doCall"
            multiParams 1
        then: "we expect them to work"
            notThrown Exception
    }

    def "default closure param becomes null if nothing is passed"() {
        given: "a closure which declares no params"
        def greet = { println "Hello, ${it}"  }
        when: "we invoke the closure"
        greet()
        then:
        "Hello, null" == output()
    }

    def "can can enforce zero parameters"() {
        given: "a closure which should take zero parameters"
        def greet = { -> println "Hello,World!" }
        when: "we try and call this with a parameter"
        greet "Hello"
        then: "we should get an exception"
        thrown MissingMethodException
    }

    def "strongly typed closures are not always suitable for using with collection methods"() {
        given: "a hetrogeneous list"
            def list = [1,3,5,7, "nine"]
        and: "a typed closure"
            def intParams = { int something -> println something; }
        when: "we use the each method of th ecollection"
            list.each intParams
        then: "Fails when we hit list[4]"
            thrown MissingMethodException
    }

    def "we can pass multiple parameters to a closure"() {
        given: "a closure which declares no params"
            def greet = { greeting, name -> println "$greeting, $name"  }
        when: "we invoke the closure"
            greet "Hello", "Dolly"
        then:
            "Hello, Dolly" == output()
    }

    def "we can define default parameters for a closure"() {
        given: "a closure with default parameters"
            def greetString = {greeting, name = "World" ->
                return "${greeting}, ${name}!"
            }
        expect:
            greetString("Hello") == "Hello, World!"
            greetString("Hello", "Dolly") == "Hello, Dolly!"
    }

    def "we can curry parameters of a closure"() {
        given: "a closure taking three parameters"
        def indian = { style, meat, rice ->
            return "$meat $style with $rice rice."
        }

        when: "we curry the closure with different first parameters"
        def vindaloo = indian.curry "Vindaloo"
        def korma = indian.curry "Korma"

        then: "it is the same as if we passed these parameters together"
        vindaloo "Chicken","Fried" == "Chicken Vindaloo with Fried rice."
        korma "Lamb","Boiled" == "Lamb Korma with Boiled rice."

        when: "we curry the closure with multiple parameters"
        def chickitikka = indian.curry "Tikka", "Chicken"

        then:
        chickitikka "Boiled" == "Chicken Tikka with Boiled rice."

        when: "we curry a curried closure"
        def lambKorma = korma.curry "Lamb"

        then:
        lambKorma "Fried" == "Lamb Korma with Fried rice."

        when: "we exhaust all the parameters"
        def lambKormaBoiled = lambKorma.curry "Boiled"

        then:
        lambKormaBoiled() == "Lamb Korma with Boiled rice."
        lambKormaBoiled() == lambKorma("Boiled")
        lambKormaBoiled() == korma("Lamb","Boiled")
        lambKormaBoiled() == indian("Korma","Lamb","Boiled")
    }

    def "we can curry parameters right to left"() {
        given: "a closure taking three parameters"
        def indian = { style, meat, rice ->
            return "$meat $style with $rice rice."
        }

        when: "we curry the closure with right parameters"
        def fried = indian.rcurry "Fried"

        then:
        fried "Vindaloo","Chicken" == "Chicken Vindaloo with Fried rice."
    }

    def "we can compose a closure from other closures"() {
        given: "two closures for f(x) = 2x + 4 and g(x) = x cubed"
            def f = { it*2 + 4}
            def g = {it * it * it}
        and: "a closure composed from these"
            def fg = f << g
        and: "a reverse composition of the same"
            def gf = f >> g
        expect:
            fg(10) == f(g(10))
            fg(7) == f(7*7*7)
            fg(13) == (2*(13*13*13)+4)
        and:
            gf(5) == g(f(5))
            gf(7) == g(7*2+4)
            gf(13) == (13*2+4)*(13*2+4)*(13*2+4)
    }

    def "we can curry the nth parameters"() {
        given: "a closure taking three parameters"
        def indian = { style, meat, rice ->
            return "$meat $style with $rice rice."
        }

        when: "we curry the closure with 2nd parameters"
        def chicken = indian.ncurry 1, "Fried"

        then:
        chicken "Vindaloo","Fried" == "Chicken Vindaloo with Fried rice."
    }

    def "closure returns can be explicit or implicit"() {
        given: "a closure that returns values"
        def closure = { param ->
            if (param == 1)
                return 1
            2
        }

        expect:
        closure(1) == 1  // return statement reached
        closure(-1) == 2 // ending statement evaluates to 2
    }

    def "void or no value for last closure statement returns null"() {
        given: "a closure returning void method"
        def nullReturn = { voidMethod() }

        expect:
        nullReturn() == null
    }

    def "closures can access variables in surrounding scope"() {
        when: "we call the greeting method"
            greeting("Dolly")
        then:
            "Hello, Dolly" == output()
    }

    def "closures in class methods can access the class scope"() {
        given: "A class with a closure in a method"
            ClosureInClassMethodScope greeter = new ClosureInClassMethodScope()
        when: "we call the class method"
            greeter.greeting "Dolly"
        then:
            "Hello, Dolly" == output()
    }

    def "closure variables are bound to instance at runtime"() {
        given: "we have a class with a method returning a closure"
            MethodReturningClosure myClazz = new MethodReturningClosure()
        when: "we invoke the method"
            def clos1 = myClazz.method("first")
        then: "member and stack variables are bound to the closure"
            clos1() == "Member: first Local: first Parameter: first"
        when: "we invoke again we get a new closure"
            myClazz.member = "second"
            def clos2 = myClazz.method("second")
        then: "new member, local and parameter values are bound"
            clos2() == "Member: second Local: second Parameter: second"
        and: "but the first still has the old parameter and locals bound"
            clos1() == "Member: second Local: first Parameter: first"
    }

    def "recursive algorithms can be replaced by Groovy closure trampoline"() {
        given: "a factorial algorithm using trampoline()"
            def factorial
            factorial = { int n, BigDecimal accumulator = 1 ->
                if (n < 2)
                    accumulator
                else
                    factorial.trampoline(n - 1, n * accumulator)
            }
        and: "we use trampoline() to wrap the closure"
            factorial = factorial.trampoline()
        expect: "it correctly calculates factorials"
            factorial(1) == 1
            factorial(3) == 1*2*3
            factorial(6) == 1*2*3*4*5*6
        when: "we use value that overflows the stack for recursion"
            factorial(10000)
        then: "it works"
            notThrown StackOverflowError
    }

    def "memoizing a closure ensures previous results are cached"() {
        given: "a simple closure"
            def callCount = 0
            def memoized = { name ->
                callCount++
                "Hello, $name $callCount"
            }
        and: "we memoize the closure"
            memoized = memoized.memoize()
        when: "we make subsequent calls with teh same parameter"
            def firstResult = memoized "Dolly"
            def secondResult = memoized "Dolly"
            def thirdResult = memoized "World"
        then:
            firstResult == secondResult
            firstResult == "Hello, Dolly 1"
            secondResult != "Hello, Dolly 2"
            thirdResult == "Hello, World 2"
    }

    def "we can memoize a trampolined closure"() {
        given: "a factorial algorithm using trampoline()"
            def trampolined
            trampolined = { int n, BigDecimal accumulator = 1 ->
                if (n < 2)
                    accumulator
                else
                    trampolined.trampoline(n - 1, n * accumulator)
            }.trampoline()
        and: "we memoize() the trampolined closure"
            def factorial = trampolined.memoize()
        expect: "it still correctly calculates factorials"
            factorial(1) == 1
            factorial(3) == 1*2*3
            factorial(6) == 1*2*3*4*5*6
    }

    def closureMethod(Closure c) {
        c.call()
    }

    def closureMethodInteger(Integer i, Closure c) {
        println "Line $i"
        c.call()
    }

    def closureMethodString(String s, Closure c) {
        println "Greet someone"
        c.call(s)
    }

    void voidMethod() {
    }

    def greeting ( name ) {
        def salutation = "Hello"

        def greeter = { println "$salutation, $name" }

        greeter()
    }


}
