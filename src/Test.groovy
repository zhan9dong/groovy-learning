def listOfMaps = [['a': 11, 'b': 12], ['a': 21, 'b': 22], null]

println(listOfMaps*.a)

assert listOfMaps*.a == listOfMaps.collect { it?.a }