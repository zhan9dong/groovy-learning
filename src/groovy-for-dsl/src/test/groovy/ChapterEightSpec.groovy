import com.dearle.groovydsl.chapters.eight.Account
import com.dearle.groovydsl.chapters.eight.Basic
import com.dearle.groovydsl.chapters.eight.Customer
import com.dearle.groovydsl.chapters.eight.LEDSwitchState
import com.dearle.groovydsl.chapters.eight.LEDToggleState
import com.dearle.groovydsl.chapters.eight.TapeDeckState
import com.dearle.groovydsl.chapters.eight.flip.LEDFlipSwitch
import com.dearle.groovydsl.chapters.eight.tape.KrappsLastTape
import com.dearle.groovydsl.chapters.eight.toggle.LEDToggle
import com.dearle.groovydsl.chapters.eight.POGO
import com.dearle.groovydsl.test.SpockScriptSpecification

class ChapterEightSpec extends SpockScriptSpecification {

    def "We can add a Basic AST transform to a class"() {
        given:
        def target = new Basic()

        when:
            target.prettyPrint()
        then:
        "I'm so pretty. Oh so pretty!" == output()

    }

    def "We can add a Simple Print AST transform to a class"() {
        given:
            def pogo = new POGO(a:1, b:2, c:3)

        when:
            pogo.prettyPrint()

        then:
        """a: 1
b: 2
c: 3""" == output()

    }

    def "We can add an Advanced Print AST transform to a class"() {
        given:
            def account = new Account(balance: 100.0)

        when:
            account.prettyPrint()

        then:
        "balance: 100.0" == output()

    }
    def "We can add an Awesome Print AST transform to a class"() {
        given:
        def customer = new Customer(id:1001,firstName:"Fred", surname:"Flintstone",
            street:"1 Rock Road",city:"Bedrock")

        when:
                customer.prettyPrint()
        then:
        """city: Bedrock
firstName: Fred
id: 1001
street: 1 Rock Road
surname: Flintstone""" == output()

    }

    def "We can build a toggle State machine in Groovy using the State Pattern"() {
        given:
            def ledToggle = new LEDToggle()

        expect:
            ledToggle.state == 'OFF'

        when:
            ledToggle.toggle()

        then:
            ledToggle.state == 'ON'

        when:
            ledToggle.toggle()

        then:
            ledToggle.state == 'OFF'
    }

    def "We can build a switching State machine in Groovy using the State Pattern"() {
        given:
            def ledSwitch = new LEDFlipSwitch()

        expect:
            ledSwitch.state == 'OFF'

        when:
            ledSwitch.switchOn()

        then:
            ledSwitch.state == 'ON'

        when:
            ledSwitch.switchOn()

        then:
            ledSwitch.state == 'ON'

        when:
            ledSwitch.switchOff()

        then:
            ledSwitch.state == 'OFF'

        when:
            ledSwitch.switchOff()

        then:
            ledSwitch.state == 'OFF'
    }

    def "We can build a complex State machine in Groovy using the State Pattern"() {
        given:
            def tape = new KrappsLastTape()
        expect:
            tape.state == 'EMPTY'

        when:
            tape.pause()
        then:
            tape.state == 'EMPTY'

        when:
            tape.start()
        then:
            tape.state == 'EMPTY'

        when:
            tape.stop()
        then:
            tape.state == 'EMPTY'

        when:
            tape.unload()
        then:
            tape.state == 'EMPTY'

        when:
            tape.load()
        then:
            tape.state == 'LOADED'

        when:
            tape.pause()
        then:
            tape.state == 'LOADED'

        when:
            tape.stop()
        then:
            tape.state == 'LOADED'

        when:
            tape.start()
        then:
            tape.state == 'RUNNING'

        when:
            tape.unload()
        then:
            tape.state == 'RUNNING'

        when:
            tape.load()
        then:
            tape.state == 'RUNNING'

        when:
            tape.stop()
        then:
            tape.state == 'LOADED'

        when:
            tape.start()
            tape.pause()
        then:
            tape.state == 'PAUSED'

        when:
            tape.load()
        then:
            tape.state == 'PAUSED'

        when:
            tape.unload()
        then:
            tape.state == 'PAUSED'

        when:
            tape.pause()
        then:
            tape.state == 'PAUSED'

        when:
            tape.start()
        then:
            tape.state == 'RUNNING'

        when:
            tape.pause()
            tape.stop()
        then:
            tape.state == 'LOADED'
    }

    def "We can build a toggle State machine in Groovy using the State DSL"() {
        given:
        def ledToggle = new LEDToggleState()

        expect:
        ledToggle.state == 'OFF'

        when:
        ledToggle.toggle()

        then:
        ledToggle.state == 'ON'

        when:
        ledToggle.toggle()

        then:
        ledToggle.state == 'OFF'
    }

    def "We can build a switching State machine in Groovy using the State DSL"() {
        given:
            def ledSwitch = new LEDSwitchState()

        expect:
            ledSwitch.state == 'OFF'

        when:
            ledSwitch.switchOn()

        then:
            ledSwitch.state == 'ON'

        when:
            ledSwitch.switchOn()

        then:
            ledSwitch.state == 'ON'

        when:
            ledSwitch.switchOff()

        then:
            ledSwitch.state == 'OFF'

        when:
            ledSwitch.switchOff()

        then:
            ledSwitch.state == 'OFF'
    }

    def "We can build a complex State machine in Groovy using the State DSL"() {
        given:
            def tape = new TapeDeckState()
        expect:
            tape.state == 'EMPTY'

        when:
            tape.pause()
        then:
            tape.state == 'EMPTY'

        when:
            tape.start()
        then:
            tape.state == 'EMPTY'

        when:
            tape.stop()
        then:
            tape.state == 'EMPTY'

        when:
            tape.unload()
        then:
            tape.state == 'EMPTY'

        when:
            tape.load()
        then:
            tape.state == 'LOADED'

        when:
            tape.pause()
        then:
            tape.state == 'LOADED'

        when:
            tape.stop()
        then:
            tape.state == 'LOADED'

        when:
            tape.start()
        then:
            tape.state == 'RUNNING'

        when:
            tape.unload()
        then:
            tape.state == 'RUNNING'

        when:
            tape.load()
        then:
            tape.state == 'RUNNING'

        when:
            tape.stop()
        then:
            tape.state == 'LOADED'

        when:
            tape.start()
            tape.pause()
        then:
            tape.state == 'PAUSED'

        when:
            tape.load()
        then:
            tape.state == 'PAUSED'

        when:
            tape.unload()
        then:
            tape.state == 'PAUSED'

        when:
            tape.pause()
        then:
            tape.state == 'PAUSED'

        when:
            tape.start()
        then:
            tape.state == 'RUNNING'

        when:
            tape.pause()
            tape.stop()
        then:
            tape.state == 'LOADED'
    }
}
