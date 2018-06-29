package com.dearle.groovydsl.chapters.ten

class PoorMansTagBuilder20 extends BuilderSupport {
  def indent = 0

  def createNode(name){
    indent.times {print "    "}
    println "<${name}>"
    indent++
    return name
  }
  def createNode(name, value){
    indent.times {print "    "}
    println "<${name}>" + value
    indent++
    return name
  }
  def createNode(name, Map attributes){
    indent.times {print "    "}
    print "<${name} "
    print attributes.collect {
      "${it.key}='${it.value}'"
    }.join(' ')
    println ">"
    indent++
    return name
  }
  def createNode(name, Map attributes, value){
    indent.times {print "    "}
    print "<${name} "
    print attributes.collect {
      "${it.key}='${it.value}'"
    }.join(' ')
    println ">" + value
    indent++
    return name
  }
  void setParent(parent, child){
    // Don't care since we are just streaming to output
  }
  void nodeCompleted(parent, node) {
    indent--
    indent.times {print "    "}
    println "</${node}>"
  }

}
