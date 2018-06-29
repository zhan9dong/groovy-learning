import com.dearle.groovydsl.test.SpockGradleSpecification
import spock.lang.Specification

class ChapterTwoSpockSpec extends Specification {
    def truth

    GroovyShell shell
    Binding binding
    PrintStream orig
    ByteArrayOutputStream out

    def setup() {
        orig = System.out
        out = new ByteArrayOutputStream()
        System.setOut(new PrintStream(out))
        binding = new Binding()
        shell = new GroovyShell(binding)
    }

    def cleanup() {
        System.setOut(orig)
    }

    void truthTest () {
        given:
            truth = true

        expect:
            truth
    }

    def "the truth could not be truer" () {
        given:
            truth = true

        expect:
            truth
    }

    def "blocks with comment strings read better than those without" () {
        given: "the unvarnished truth"
            truth = true

        expect: "its true"
            truth
    }

    def "two wrongs don't make a right" () {
        given: "two false statements"
            def theWorldIsFlat = false
            def theEarthOrbitsTheSun = false

        when: "we combine the two falsehoods"
            def copernicusWasWrong = theWorldIsFlat && theEarthOrbitsTheSun

        then: "Copernicus was telling the truth"
            ! copernicusWasWrong
    }

    def "we can extend Spock specs with and: blocks" () {
        given: "Two Integer numbers"
            Number a = 10
            Number b = 5

        expect: "Integer multiplication and addition are commutative"
            a * b == b * a

        and:
            a + b == b + a
    }

    def "HelloWorld says Hello World"() {
        given: "we have a Hello World script"
            def script = new File("scripts/ChapterTwo/Hello.groovy")

        when: "we run the script"
            shell.evaluate script

        then: "the script outputs the correct details"
            "Hello, World!" == output()
    }

    def "HelloWorld says Hello World in Java and Groovy styles"() {
        given: "we have a Hello World script"
            def script = new File(fileName)

        when: "we run the script"
            shell.evaluate script

        then: "the script outputs the correct details"
            expectedOutput == output()

        where: "we have different versions of HelloWorld using more groovy features"
            fileName                                  | expectedOutput
            "scripts/ChapterTwo/Hello.groovy"         | "Hello, World!"
            "scripts/ChapterTwo/HelloWorld.groovy"    | "Hello, World!"
    }

    def "Fixtures can be in blocks too"() {
        setup:
            orig = System.out
            out = new ByteArrayOutputStream()
            System.setOut(new PrintStream(out))
            binding = new Binding()
            shell = new GroovyShell(binding)

        and: "we have a Hello World script"
            def script = new File("scripts/ChapterTwo/Hello.groovy")

        when: "we run the script"
            shell.evaluate script

        then: "the script outputs the correct details"
            "Hello, World!" == output()

        cleanup:
            System.setOut(orig)

    }

    def String output() {
        out.toString().trim()
    }

}
