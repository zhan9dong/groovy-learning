import com.dearle.groovydsl.chapters.seven.Account
import com.dearle.groovydsl.chapters.seven.BankManager
import com.dearle.groovydsl.chapters.seven.Customer
import com.dearle.groovydsl.chapters.seven.CustomerPrinter
import com.dearle.groovydsl.chapters.seven.CustomerWithTrait
import com.dearle.groovydsl.chapters.seven.CustomerWithInvokeMethod
import com.dearle.groovydsl.chapters.seven.Clazz
import com.dearle.groovydsl.chapters.seven.POGO
import com.dearle.groovydsl.chapters.seven.PoorMansTagBuilder
import com.dearle.groovydsl.chapters.seven.BusinessService
import static com.dearle.groovydsl.chapters.seven.Methods.*
import static com.dearle.groovydsl.chapters.seven.Methods.Currency.*
import com.dearle.groovydsl.test.SpockScriptSpecification
import org.apache.commons.lang3.StringUtils
import com.github.lalyos.jfiglet.FigletFont

class ChapterSevenSpec extends SpockScriptSpecification   {

    def "We can construct a POGO using named parameters (Map paramters)"() {
        given:
            def pogo1 = new POGO(a:1, b:2, c:3)
            def pogo2 = new POGO( b:2, c:3)
            def pogo3 = new POGO(b:2, a:3)

        expect:
            pogo1.a == 1
            pogo1.b == 2
            pogo1.c == 3

        and:
            pogo2.a == 0
            pogo2.b == 2
            pogo2.c == 3

        and:
            pogo3.a == 3
            pogo3.b == 2
            pogo3.c == 0
    }

    def "Named parameters give us great flexibility with parameter passing"() {
        expect: "We can pass named parameters in any order"
            namedParamsMethod1(a:1, b:2, c:3)
            namedParamsMethod1(b:2, c:3, a:1)
            namedParamsMethod1(c:3, a:1, b:2)

        and: "We can mix named and regular parameter in any order"
            namedParamsMethod2(a:1, b:2, c:3, "param2", "param3")
            namedParamsMethod2("param2", b:2, "param3", c:3, a:1)
            namedParamsMethod2(c:3, "param2", a:1, "param3", b:2)

        and: "We can leave out parentheses"
            namedParamsMethod2 a:1, b:2, c:3, "param2", "param3"
            namedParamsMethod2 "param2", b:2, "param3", c:3, a:1
            namedParamsMethod2 c:3, "param2", a:1, "param3", b:2
    }

    def "we can define a transfer method with regular parameters" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterSeven/transfer1.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """debiting 100.00 from checking account,
crediting savings account for Joe Bloggs""" == output()
    }

    def "we can define a transfer method with name parameters" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterSeven/transfer2.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """debiting 100.00 from checking account,
crediting savings for Joe Bloggs
debiting 200.00 from checking account,
crediting savings for Joe Bloggs""" == output()
    }

    def "named parameters allow us to mix the order of parameters"() {
        given:
            sendMessage1 "GroovyDSL", "Hi from GeeTwitter"
        expect: "message sent correctly"
            sentMessage == "Sending (Hi from GeeTwitter) to GroovyDSL"

        when:
            sendMessage1 "Hi from GeeTwitter", "GroovyDSL"
        then: "message sent incorrectly"
            ! (sentMessage == "Sending (Hi from GeeTwitter) to GroovyDSL")

        when:
            sendMessage2 to: "GroovyDSL", "Hi from GeeTwitter"
        then: "message sent correctly"
            sentMessage == "Sending (Hi from GeeTwitter) to GroovyDSL"

        when:
            sendMessage2 "Hi from GeeTwitter", to: "GroovyDSL"
        then: "message sent incorrectly"
            sentMessage == "Sending (Hi from GeeTwitter) to GroovyDSL"
    }

    def "We can construct a POGO using fluent api via @Builder"() {
        given:
            def pogo1 = new POGO().builder().a(1).b(2).c(3).build()
            def pogo2 = new POGO().builder().b(2).c(3).build()
            def pogo3 = new POGO().builder().b(2).a(3).build()

        expect:
            pogo1.a == 1
            pogo1.b == 2
            pogo1.c == 3

        and:
        println pogo2
            pogo2.a == 0
            pogo2.b == 2
            pogo2.c == 3

        and:
            pogo3.a == 3
            pogo3.b == 2
            pogo3.c == 0
    }

    def "command chains are more expressive"() {
        given:
        Account savings = new Account()

        when:
        deposit (100.00).currency(USD).to(savings)
        deposit 100.00 currency GBP to savings

        then:
        savings.balance == 230.0
    }

    def "markup builder"() {
        given:
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)
        when:

        def customers = builder.customers {
            customer(id:1001) {
                name(firstName:"Fred",surname:"Flintstone")
                address(street:"1 Rock Road",city:"Bedrock")
            }
            customer(id:1002) {
                name(firstName:"Barney",surname:"Rubble")
                address(street:"2 Rock Road",city:"Bedrock")
            }
        }
        then:
            xmlIsIdentical (writer.toString(), "src/main/xml/ChapterSeven/customers.xml" )
    }

    def "markeup builder with namespaces"() {
        given:
        def writer = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(writer)

        def params = [:]

        when:
        params."xmlns:bk" = "urn:loc.gov:books"
        params."xmlns:isbn" = "urn:ISBN:0-393-36341-6"

        def bk_tag = "bk:book"
        xml."bk:book"(params) {
            "bk:title"("Cheaper by the Dozen")
            "isbn:number"(1568491379)
        }

        then:
            xmlIsIdentical writer.toString(), "src/main/xml/ChapterSeven/book1.xml"

    }

    def "markeup builder with namespaces 2"() {
        given:
        def writer = new StringWriter()
        def xml = new groovy.xml.MarkupBuilder(writer)

        def book = "bk-book"
        def book_title = "bk-title"

        when:
        xml."${book}" {
            "${book_title}"("Cheaper by the Dozen")
            "isbn_number"(1568491379)
        }
        then:
            xmlIsIdentical writer.toString(), "src/main/xml/ChapterSeven/book2.xml"
    }

    def "streaming markup builder"() {
        given:
        def xml = new groovy.xml.StreamingMarkupBuilder()

        when:
        def markup = {
            customers {
                customer(id:1001) {
                    name(firstName:"Fred",surname:"Flintstone")
                    address(street:"1 Rock Road",city:"Bedrock")
                }
                customer(id:1002) {
                    name(firstName:"Barney",surname:"Rubble")
                    address(street:"2 Rock Road",city:"Bedrock")
                }
            }
        }

        then:
            xmlIsIdentical xml.bind( markup ).toString(), "src/main/xml/ChapterSeven/customers.xml"
    }

    def "streaming markup builder xml header"() {
        given:
            def xml = new groovy.xml.StreamingMarkupBuilder().bind {
                mkp.xmlDeclaration()
                mkp.declareNamespace('bk':'urn:loc.gov:books')
                mkp.declareNamespace('isbn':'urn:ISBN:0-393-36341-6')

                bk.book {
                    bk.title("Cheaper by the Dozen")
                    isbn.number(1568491379)
                }
            }

        expect:
            xmlIsIdentical xml.toString(), "src/main/xml/ChapterSeven/book1.xml"
    }

    def "html markup 1"() {
        given:
        def writer = new StringWriter()
        def html = new groovy.xml.MarkupBuilder(writer)

        when:
        html.html {
            head {
                title "Groovy Builders"
            }
            body {
                h1 "Groovy Builders are cool!"
            }
        }

        then:
        xmlIsIdentical writer.toString(), "src/main/xml/ChapterSeven/simple.html"

    }

    def "html maprkup 2"() {
        given:
        def writer = new StringWriter()
        def html = new groovy.xml.MarkupBuilder(writer)

        when:
        html.html {
            head {
                title "Groovy Builders"
            }
            body {
                table(style:"border: 1px solid black;") {
                    tr {
                        th "Builder class"
                        th "Concrete class"
                    }
                    tr {
                        td "groovy.util.BuilderSupport"
                        td {
                            table {
                                tr {
                                    td "groovy.util.AntBuilder"
                                }
                                tr {
                                    td "groovy.xml.MarkupBuilder"
                                }
                             }
                        }
                    }
                    tr {
                        td "groovy.util.FactoryBuilderSupport"
                        td {
                            table {
                                tr {
                                    td "groovy.util.NodeBuilder"
                                }
                                tr {
                                    td "groovy.swing.SwingBuilder"
                                }
                             }
                        }
                    }
                }
            }
        }
        then:
        xmlIsIdentical writer.toString(), "src/main/xml/ChapterSeven/table.html"


    }

    def "we can add program logic to our builder code"() {
        given:
        def writer = new StringWriter()
        def builder = new groovy.xml.MarkupBuilder(writer)

        def fred = new Customer(id:1001,firstName:"Fred", surname:"Flintstone",
        street:"1 Rock Road",city:"Bedrock")
        def barney =  new Customer(id:1002,firstName:"Barney", surname:"Rubble",
        street:"2 Rock Road",city:"Bedrock")
        def customerList = [ fred, barney]

        when:
        builder.customers {
            for (cust in customerList) {
                customer(id:cust.id) {
                    name(firstName:cust.firstName,surname:cust.surname)
                    address(street:cust.street, city:cust.city)
                }
            }
        }

        then:
        xmlIsIdentical writer.toString(), "src/main/xml/ChapterSeven/customers.xml"
    }

    def "Using NodeBuiler "() {
        given:
        def builder = new groovy.util.NodeBuilder()

        def fred = new Customer(id:1001,firstName:"Fred", surname:"Flintstone",
            street:"1 Rock Road",city:"Bedrock")
        def barney =  new Customer(id:1002,firstName:"Barney", surname:"Rubble",
            street:"2 Rock Road",city:"Bedrock")
        def wilma = new Customer(id:1003,firstName:"Wilma", surname:"Flintstone",
            street:"1 Rock Road",city:"Bedrock")
        def betty =  new Customer(id:1004,firstName:"Betty", surname:"Rubble",
            street:"2 Rock Road",city:"Bedrock")
        def customerList = [ fred, barney,wilma,betty]

        when:
        def customers = builder.customers {
            for (cust in customerList) {
                customer(id:cust.id) {
                    name(firstName:cust.firstName,surname:cust.surname)
                    address(street:cust.street, city:cust.city)
                }
            }
        }

        then:
        customers.customer[0].'@id' == 1001
        customers.customer[1].'@id' == 1002
        customers.customer[0].address[0].'@street' ==
                               customers.customer[2].address[0].'@street'
        customers.grep{
             it.name.any{it.'@surname' == "Rubble"}
        }.size == 2
        customers.grep{
             it.name.any{it.'@surname' == "Rubble"}
        }.address.every{ it.'@street'[0] == "2 Rock Road"}

    }

    def "We can assign a method to a closure" () {
        given:
        def list = ["A", "B", "C"]

        when:
        def addToList = list.&add

        and:
        addToList "D"

        then:
        list ==  ["A", "B", "C", "D"]

    }

    def "groovy has builtin reflection helper methods"() {
        given:
            def greeting = "Hello"
        expect:
            greeting.class.package == String.package
            String.package.toString().contains "package java.lang"
    }

    def "expandos"() {
        given:
            def customer = new Expando()

        expect:
            customer.properties == [:]
            customer.id == null
            customer.properties == [:]

        when:
            customer.id = 1001
            customer.firstName = "Fred"
            customer.surname = "Flintstone"
            customer.street = "1 Rock Road"

        then:
            customer.id == 1001

            customer.properties == [
                id:1001, firstName:'Fred',
                surname:'Flintstone', street:'1 Rock Road']

    }

    def "adding a closure to an expando acts like a new method"() {
        given: "we have a script to run"
            def script = new File("scripts/ChapterSeven/PrettyPrint.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """Customer has following properties
    firstName: Fred
    id: 1001
    street: 1 Rock Road
    surname: Flintstone""" == output()

    }

    def "categories"() {
        given:
        def fred = new Customer(id:1001,firstName:"Fred", surname:"Flintstone",
            street:"1 Rock Road",city:"Bedrock")
        def barney =  new Customer(id:1002,firstName:"Barney", surname:"Rubble",
            street:"2 Rock Road",city:"Bedrock")

        def customerList = [ fred, barney]

        when:
        use (CustomerPrinter) {
            for (customer in customerList)
                customer.prettyPrint()
        }
        then:
        """Customer has following properties
    city: Bedrock
    firstName: Fred
    id: 1001
    street: 1 Rock Road
    surname: Flintstone
Customer has following properties
    city: Bedrock
    firstName: Barney
    id: 1002
    street: 2 Rock Road
    surname: Rubble""" == output()

    }

    def "We can use an existing Utility class as if it were a category"() {
        expect:
            use (StringUtils) {
                "org.apache.commons.lang3".split(".") == ["org", "apache", "commons", "lang3"]
            }
    }

    def "traits"() {
        given:
        def fred = new CustomerWithTrait(id:1001,firstName:"Fred", surname:"Flintstone",
            street:"1 Rock Road",city:"Bedrock")
        def barney =  new CustomerWithTrait(id:1002,firstName:"Barney", surname:"Rubble",
            street:"2 Rock Road",city:"Bedrock")

        def customerList = [ fred, barney]

        when:
        for (customer in customerList)
            customer.prettyPrint()
        then:
        """city: Bedrock
firstName: Fred
id: 1001
street: 1 Rock Road
surname: Flintstone
city: Bedrock
firstName: Barney
id: 1002
street: 2 Rock Road
surname: Rubble""" == output()

    }

    def "pretended method"() {
        given:
        def fred = new CustomerWithInvokeMethod(id:1001,firstName:"Fred", surname:"Flintstone",
            street:"1 Rock Road",city:"Bedrock")
        def barney =  new CustomerWithInvokeMethod(id:1002,firstName:"Barney", surname:"Rubble",
            street:"2 Rock Road",city:"Bedrock")

        def customerList = [ fred, barney]

        when:
        for (customer in customerList)
            customer.prettyPrint()
        then:
        """Customer has following properties
    city: Bedrock
    firstName: Fred
    id: 1001
    street: 1 Rock Road
    surname: Flintstone
Customer has following properties
    city: Bedrock
    firstName: Barney
    id: 1002
    street: 2 Rock Road
    surname: Rubble""" == output()

    }

    def "this, owner delegate"() {
        given: "A class with an Instance method"
        def clazz = new Clazz()

        when: "In a class instance method 'this' is the "
        def methodThis = clazz.method()

        then:
        methodThis == clazz

        when: "we try to access delegate from a method"
        def methodDelegate = clazz.methodDelegate()

        then:
        thrown(MissingPropertyException)
        methodDelegate == null

        when: "we try to access owner from a method"
        def methodOwner = clazz.methodOwner()

        then:
        thrown(MissingPropertyException)
        methodOwner == null

        when: "we get this, delegate and owner for a closure"
        def (closureThis, closureDelegate, closureOwner) = clazz.closure()

        then: "this, delegate and owner are the class instance"
        closureThis == clazz
        closureDelegate == clazz
        closureOwner == clazz

        when: "get this, delegate and owner for a closure in method"
        def (closureInMethodThis, closureInMethodDelegate, closureInMethodOwner) =
            clazz.closureWithinMethod()


        then: """this, delegate and owner are the class instance not the
                 enclosing closure"""
        closureInMethodThis == clazz
        closureInMethodDelegate == clazz
        closureInMethodOwner == clazz
        closureInMethodThis != clazz.closure
        closureInMethodDelegate != clazz.closure
        closureInMethodOwner != clazz.closure
    }

    def "a simple tag builder with invokeMethod"() {
given:
def builder = new PoorMansTagBuilder ()

when:
builder.root {
       level1{
        level2 {
       }
    }
}

then:
        """<root>
    <level1>
        <level2>
        </level2>
    </level1>
</root>""" == output()

    }

    def "We can add a method to an existing class with ExpandoMetaClass"() {
        given:
        String.metaClass.toFiglet = {
            FigletFont.convertOneLine(delegate)
        }
        //     _  ___
        //    | ||_ _|
        // _  | | | |
        //| |_| | | |
        // \___/ |___|
        //
        expect:
        "J".toFiglet() == "     _ \n    | |\n _  | |\n| |_| |\n \\___/ \n       \n"
        "I".toFiglet() == " ___ \n|_ _|\n | | \n | | \n|___|\n     \n"
    }

    def "We can replace a method in a class"() {
        given:
        def myBankManager = new BankManager()

        expect:
        myBankManager.approveLoan() == false

        when:
        BankManager.metaClass.approveLoan = { true }
        myBankManager = new BankManager()

        then:
        myBankManager.approveLoan() == true

    }

    def "we can mock a static method of a service"() {
        when:
            BusinessService.isRemoteServiceLive()
        then:
            thrown NullPointerException

        when:
            BusinessService.metaClass.static.isRemoteServiceLive = { true }
            def live = BusinessService.isRemoteServiceLive()

        then:
            notThrown NullPointerException
            live == true

    }

    def "we can dynamically name methods"() {
        given:
        def c = new Customer()
        c.properties.keySet().findAll { !(it =~ /lass/)}.each {
            Customer.metaClass."idFor${it.capitalize()}" = { ->
                delegate."$it".toString().toLowerCase().tr(' ', '_')
            }
        }
        when:
        def cust = new Customer(firstName:"Fred",
                         surname:"Flintstone",
                         street:"Rock Road",
                         city:"Bedrock")

        then:
        cust.idForFirstName() == "fred"
        cust.idForSurname() == "flintstone"
        cust.idForStreet() == "rock_road"
    }

    def "the last version of a methods added is the one that is active"() {
        given:
        String.metaClass.blanks { delegate.replaceAll(/./) {'%'}}
        String.metaClass.blanks { delegate.replaceAll(/./) {'@'}}
        String.metaClass.blanks { delegate.replaceAll(/./) {'*'}}
        expect:
        "A String".blanks() == "********"
    }

    def "if the signature is different we continue adding overloaded methods"() {
        given:
        String.metaClass.static.valueAndType = { double d ->
            try {
                "${d.class.name}:${String.valueOf(d)}"
            } catch (e ) {
                println e.message
                throw e
            }
        }
        String.metaClass.static.valueAndType = { float f ->
            "${f.class.name}:${String.valueOf(f)}"
        }
        String.metaClass.static.valueAndType = { int i ->
            "${i.class.name}:${String.valueOf(i)}"
        }
        String.metaClass.static.valueAndType = { long l ->
            "${l.class.name}:${String.valueOf(l)}"
        }

        expect:
        String.valueAndType(1.0) == "java.lang.Double:1.0"
        String.valueAndType(3.333f) == "java.lang.Float:3.333"
        String.valueAndType(101) == "java.lang.Integer:101"
        String.valueAndType(1000000L) == "java.lang.Long:1000000"
    }

    def "method overloading can be done using the append operator <<"() {
         given:
         String.metaClass.static.valueAndType << { double d ->
             "${d.class.name}:${String.valueOf(d)}"
         }
         String.metaClass.static.valueAndType << { float f ->
             "${f.class.name}:${String.valueOf(f)}"
         }
         String.metaClass.static.valueAndType << { int i ->
             "${i.class.name}:${String.valueOf(i)}"
         }
         String.metaClass.static.valueAndType << { long l ->
             "${l.class.name}:${String.valueOf(l)}"
         }

         expect:
         String.valueAndType(1.0) == "java.lang.Double:1.0"
         String.valueAndType(3.333f) == "java.lang.Float:3.333"
         String.valueAndType(101) == "java.lang.Integer:101"
         String.valueAndType(1000000L) == "java.lang.Long:1000000"
     }

    def "we can add constructors to a class"() {
        given:
        Customer.metaClass.constructor  = {
            String first, String last -> new Customer(
        		firstName:first,
        		surname:last)
        }

        when:
        def c = new Customer("Fred", "Flintstone" )

        then:
        c.firstName == "Fred"
        c.surname == "Flintstone"
    }
}