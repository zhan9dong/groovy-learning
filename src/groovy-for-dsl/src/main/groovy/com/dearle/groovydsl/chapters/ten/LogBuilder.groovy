package com.dearle.groovydsl.chapters.ten

class LogBuilder extends BuilderSupport {
    def indent = 0
    def createNode(name){
		indent.times {print "  "}
		println "createNode(${name})"
		indent++
		return name
    }
    def createNode(name, value){
		indent.times {print "  "}
		println "createNode(${name}, ${value})"
		indent++
		return name
    }
    def createNode(name, Map attributes){
		indent.times {print "  "}
		println "createNode(${name}, ${attributes})"
		indent++
		return name
    }
    def createNode(name, Map attributes, value){
		indent.times {print "  "}
		println "createNode(${name}, ${attributes}, ${value})"
		indent++
		return name
    }
    void setParent(parent, child){
		indent.times {print "  "}
		println "setParent(${parent}, ${child})"
    }
    void nodeCompleted(parent, node) {
		indent--
		indent.times {print "  "}
		println "nodeCompleted(${parent}, ${node})"
    }
}
