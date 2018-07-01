
def closr = {x -> x + 1}

println( closr(2) )

//def closr = {it + 1}

def list = ['foo','bar']
def newList = []
list.collect( newList ) {
  it.toUpperCase()
}
println newList //  ["FOO", "BAR"]



def find(list, tester) {
for (item in list)
    if (tester(item)) return item
}

find([1,2]) { it > 1 }

