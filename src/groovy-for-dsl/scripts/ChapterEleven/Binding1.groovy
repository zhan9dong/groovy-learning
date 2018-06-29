count = 1

assert count == 1
assert binding.getVariable("count") == 1
binding.setVariable("count" , 2)
assert count == 2
assert binding.getVariable("count") == 2 

def local = count 

assert local == 2
try {
    binding.getVariable("local")
    assert false
} catch (e) {
    assert e in MissingPropertyException
}

println "Success"