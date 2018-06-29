println "Package for String class"
println "    " + String.package
println "All methods of Object class:"
Object.methods.each { println "    " + it }
println "All fields of Integer class:"
Integer.fields.each { println "    " + it }
