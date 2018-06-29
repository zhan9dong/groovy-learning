binding.count = 1

assert binding.getProperty("count") == 1

binding.setProperty("count" , 2)

assert binding.count == 2

println "Success"