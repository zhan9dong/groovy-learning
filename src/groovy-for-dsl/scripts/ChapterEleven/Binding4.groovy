def Binding binding = new Binding()

binding.message = "Hello, World!"

shell = new GroovyShell(binding)

shell.evaluate("println message")
