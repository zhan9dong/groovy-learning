def binding = new Binding()
binding.saved = {
}

binding.deferred = { closure ->
   closure.delegate = delegate
   closure()
}

def shell = new GroovyShell(binding)
shell.evaluate(
"""
saved = {
      println "saved"
        deferred {
         println "deferred"
        }
   }
"""
)
def storeSaved = binding.saved

storeSaved()

