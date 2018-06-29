binding.setVariable("count" , 1)

assert binding.getVariable("count") == 1

binding.setVariable("count" , 2)

assert binding.getVariable("count") == 2

println "Success"
