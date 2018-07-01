
def concat = { x, y -> return x + y }
// closure
def burn = concat.curry("burn")
def inate = concat.curry("inate") 

burn(" wood") // == burn wood

def composition = { f, g, x -> return f(g(x)) }
def burninate = composition.curry(burn, inate)
def trogdor = burninate(' all the people')
println "Trogdor: ${trogdor}"
// Trogdor: burninate all the people


def breathFire(name) { println "Burninating $name!" }
    
['the country side', 'all the people'].each(this.&breathFire)
