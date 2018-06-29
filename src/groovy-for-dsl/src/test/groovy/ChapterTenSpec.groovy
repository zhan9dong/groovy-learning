

import com.dearle.groovydsl.chapters.nine.Markup
import com.dearle.groovydsl.chapters.ten.LogBuilder
import com.dearle.groovydsl.chapters.ten.PoorMansTagBuilder
import com.dearle.groovydsl.chapters.ten.PoorMansTagBuilder20
import com.dearle.groovydsl.test.SpockScriptSpecification
import static com.dearle.groovydsl.chapters.ten.Methods.*

class ChapterTenSpec extends SpockScriptSpecification {

    def "using builder style syntax with closures"() {
given:
method1(param: "one") { // Closure1 scope
    method2(greet: true) { // Closure2 scope
        method3 greeting: "hello"
    } // End Closure2
    method1( number: 123 ) { // Closure3 scope
        method1 ( nestedcall: "nested" ) { // Closure4 scope
            method3 number: 10
        } // End Closure4
    } // End Closure3
} // End Closure1

expect:
"""method1: [param:one]
method2: [greet:true]
method3: [greeting:hello]
method1: [number:123]
method1: [nestedcall:nested]
method3: [number:10]""" == output()

    }

    def "pseudo builder"() {
        given:
        // pseudo builder code
        def tree = root {
            node("sub-tree-1") {
                leaf "leaf-1", "leaf object 1"
            }
            node ("sub-tree-2"){
                node ("node-1"){
                    leaf "leaf-2", "leaf object 2"
                }
            }
        }

        expect:
        tree == [
        root: [
            "sub-tree-1": [
                "leaf-1": "leaf object 1"
                ],
             "sub-tree-2": [
                "node-1": [
                    "leaf-2": "leaf object 2"
                    ]
                ]
            ]
         ]
    }

    def "a simple tag builder with methodMissing"() {
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

    def "Simple builder with BuilderSupport"() {
        given:
        def builder = new LogBuilder()

        def customers = builder.customers {
            customer{
        	  id(1001)
                name(firstName:"Fred",surname:"Flintstone")
                address("billing", street:"1 Rock Road",city:"Bedrock")
                address("shipping", street:"1 Rock Road",city:"Bedrock")
            }
        }
        expect:
"""createNode(customers)
  createNode(customer)
    setParent(customers, customer)
    createNode(id, 1001)
      setParent(customer, id)
    nodeCompleted(customer, id)
    createNode(name, [firstName:Fred, surname:Flintstone])
      setParent(customer, name)
    nodeCompleted(customer, name)
    createNode(address, [street:1 Rock Road, city:Bedrock], billing)
      setParent(customer, address)
    nodeCompleted(customer, address)
    createNode(address, [street:1 Rock Road, city:Bedrock], shipping)
      setParent(customer, address)
    nodeCompleted(customer, address)
  nodeCompleted(customers, customer)
nodeCompleted(null, customers)""" == output()
    }

    def "a tag builder using BuilderSupport"() {
        given:
        def builder = new PoorMansTagBuilder20 ()

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
    def current
    def root (Closure closure) {
        def tree = [:]
        def root = [:]
        tree["root"] = root
        def parent = current
        current = root
        closure.call()
        current = parent
        return tree
    }

    def node (key, Closure closure) {
        def node = [:]
        current[key] = node
        def parent = current
        current = node
        closure.call()
        current = parent
    }

    def leaf (key, value ) {
        current[key] = value

    }
}
