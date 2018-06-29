def Binding binding = new Binding()

binding.greet = { subject ->
    println "Hello, $subject!"
}

shell = new GroovyShell(binding)

shell.evaluate("greet 'World'")
