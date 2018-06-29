package com.dearle.groovydsl.test

import spock.lang.*
import org.custommonkey.xmlunit.*

class SpockScriptSpecification  extends Specification {
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

    def xmlIsIdentical(String xml, String fileName) {
        def xmlText = new File(fileName).text
        XMLUnit.setIgnoreWhitespace(true)
        def xmlDiff = new Diff(xml, xmlText)
        xmlDiff.identical()
    }

    def cleanup() {
        System.setOut(orig)
    }

    protected String output() {
        out.toString().trim()
    }
}
