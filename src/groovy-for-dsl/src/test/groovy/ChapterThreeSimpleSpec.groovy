import spock.lang.Specification

class ChapterThreeSimpleSpec extends Specification {

    void "the truth could not be truer" () {
        given: "the unvarnished truth"
            def truth = true

        expect: "its true"
            truth
    }

}
