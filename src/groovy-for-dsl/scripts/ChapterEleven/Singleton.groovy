def binding = new Binding()
binding.setup = {
		println "Setup block is missing"
		throw new Exception("Setup block is missing")
}

binding.teardown = {
		println "Teardown block is missing"
		throw new Exception("Teardown block is missing")
}

def shell = new GroovyShell(binding)
shell.evaluate(
"""setup = {
		'setup called'
	}
	teardown = {
		'teardown called'
	}
"""
)

setup = binding.setup
assert setup() == 'setup called'
// ... do something now and save teardown closure for later
teardown = binding.teardown
assert teardown() == 'teardown called'

println "Success"
