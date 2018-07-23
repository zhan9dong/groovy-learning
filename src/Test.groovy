String.metaClass.uppers = { -> toUpperCase() };

println "aaa".uppers()


Integer.metaClass.say = { -> "I am Interger" }

def i = new Integer(100);

println i.say()