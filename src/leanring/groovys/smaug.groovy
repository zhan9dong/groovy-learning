import groovy.transform.*

@Canonical 
class Dragon {
    final age
    String name
    public Dragon(name, age = 1000) {this.name = name; this.age = age}
    def lines = ["I smell you!", "I hear your breath"]
    def count = 0
    def fly(String text = " is flying") {println name+text}
    def talk() {println lines[count++]}
    
}

def d = new Dragon("Smaug")

d.fly(" the terrible")
println "${d.name} is ${d.age} years old"
for (i in 0..<2) {d.talk()}
