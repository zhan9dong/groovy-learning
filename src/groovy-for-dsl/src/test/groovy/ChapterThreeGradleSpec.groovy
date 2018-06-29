import com.dearle.groovydsl.test.SpockGradleSpecification
import spock.lang.Specification

class ChapterThreeGradleSpec extends SpockGradleSpecification {

    def "hello task says Hello, World!"() {
        given: "we have a gradle build command"
            def command = "gradle -q -b scripts/ChapterThree/hello.gradle hello"

        when: "we run the build command"
            def proc = command.execute()
            proc.waitFor()

        then: "the script outputs the correct details"
            "Hello, World!" == proc.in.text.trim()
    }

    def "helloSimple task says Hello, Simple World!"() {
        when: "we run the the helloSimple task"
            runGradleTask('scripts/ChapterThree/hello.gradle', 'helloSimple')

        then: "the script outputs the correct details"
            "Hello, Simple World!" == output()
    }

    def "helloWithActions task says Hello, World Actions!"() {
        when: "we run the build command"
            runGradleTask('scripts/ChapterThree/hello.gradle', 'helloWithActions')

        then: "the script outputs the correct details"
            "Hello, World Actions!" == output()
    }

    def "doFirst and doLast apply actions to positions in the order they are encountered" () {
        when: "we run the build command"
            runGradleTask('scripts/ChapterThree/hello.gradle', 'confused')

        then: "the script outputs the correct details"
            """The last doFirst will be first
The first doFirst will be last
The first doLast will be first
The last doLast will be last""" == output()

    }

    def "helloWithDepends task says outputs two lines from two tasks"() {
        when: "we run the build command"
            runGradleTask('scripts/ChapterThree/hello.gradle', 'helloWithDepends')

        then: "the script outputs the correct details"
            """Hello, Simple World!
Hello, Dependent World!""" == output()
    }

    def "default tasks are run when no task is invoked on command line"() {
        when: "we run the build command"
            runGradleTask('scripts/ChapterThree/default.gradle')

        then: "the script outputs the correct details"
            """Cleaning
Running Tests""" == output()
    }

    def "We can chain dependenent tasks"() {
        when: "we run that command"
            runGradleTask('scripts/ChapterThree/default.gradle', 'deploy')

        then: "the script outputs the dependent tasks in order."
            """Cleaning
Building Application
Running Tests
Deploying""" == output()
    }

}
