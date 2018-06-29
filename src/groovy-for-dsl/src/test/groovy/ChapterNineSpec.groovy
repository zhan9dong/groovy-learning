

import com.dearle.groovydsl.chapters.eight.*
import com.dearle.groovydsl.chapters.eight.flip.LEDFlipSwitch
import com.dearle.groovydsl.chapters.eight.tape.KrappsLastTape
import com.dearle.groovydsl.chapters.eight.toggle.LEDToggle
import com.dearle.groovydsl.chapters.nine.Markup
import com.dearle.groovydsl.test.SpockScriptSpecification

class ChapterNineSpec extends SpockScriptSpecification {
    def "we can invoke static MarkupBuilder closure as a method invokation" () {
        given:
        Markup.markup.setDelegate(new groovy.xml.MarkupBuilder())

        when:
        Markup.markup() // Outputs xml

        then:
"""<customers>
  <customer id='1001'>
    <name firstName='Fred' surname='Flintstone' />
    <address street='1 Rock Road' city='Bedrock' />
  </customer>
  <customer id='1002'>
    <name firstName='Barney' surname='Rubble' />
    <address street='2 Rock Road' city='Bedrock' />
  </customer>
</customers>""" == output()

     }


    def "initial size is zero"() {
      given:
      def stack = new Stack()
      expect: stack.size() == 0
    }

    def "pop from empty stack throws exception"() {
      given:
      def stack = new Stack()
      when: stack.pop()
      then: thrown(EmptyStackException)
    }

    def "peek from empty stack throws exception"() {
      given:
      def stack = new Stack()
      when: stack.peek()
      then: thrown(EmptyStackException)
    }

    def "after push size is one and we can peek"() {
      given:
      def stack = new Stack()

      when: stack.push("elem")

      then:
      stack.size() == 1
      stack.peek() == "elem"
    }
}
