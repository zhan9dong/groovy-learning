// REGEX = REGULAR EXPRESSIONS

def list = ['Mercury', 'Gemini', 'Apollo', 'Curiosity', 'Phoenix']

assert list.any { it =~ /r/ }              // =~ means find anywhere
assert list.every { it ==~ /[A-Z][a-z]+/ } // ==~ means match the whole string

assert list.find { it ==~ 'Apollo' } == 'Apollo'
assert list.findAll { it.size() < 6 } == []

assert list.findIndexOf { name -> name =~ /^C.*/ } == 3  // Start with C.

assert list.findIndexValues { it =~ /(y|Y)/ } == [0,3] // Contains y or Y.
assert list.findIndexValues(2) { it =~ /(y|Y)/ } == [3] // starting index of 2
assert list.findLastIndexOf { it.size() == 6 } == 2
assert list.findLastIndexOf { it.count('e') >= 1 } == 4 // count = number of occurances

def map = [name: 'Groovy and Grails', url: 'http://groovy.codehaus.org', blog: false]

def found = map.find { it.value =~ /Groovy/ }

assert found.key == 'name' && found.value == 'Groovy and Grails'

assert map.findAll { key, value ->
   value =~ /(G|g)roovy/
} == [name: 'Groovy and Grails', url: 'http://groovy.codehaus.org']

assert map.findIndexOf { it.value.endsWith('org') } == 1
assert map.findIndexValues { it.key =~ /l/ } == [1,2]  // All keys with the letter 'l'.
assert map.findLastIndexOf { it.key =~ /l/ && !it.value } == 2

// REGEX in switch
Closure test = {
    switch (it) { 
        case ~/Ja.+/: return 1
        case ~/Gr.*/: return 2
        default: return 404
    }}

assert test("Java") == 1
assert test("Groovy") == 2

