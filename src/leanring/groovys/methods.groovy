import groovy.transform.*

@Canonical class Dragon {
    String name

    def fly() {println "$name flying"}

    // default values:
    def say(String text = "I hear you") {println text}
}

    Dragon dragon = new Dragon('Smaug');
    dragon.fly(); // dragon is the object, and fly is the method

    // java-style methods
    public void fly() {
        // flying code
    }
    public void fly(int x, int y) {
        // fly to that x, y coordinate.
    }
    
    // java-style
    public Dragon makeDragonNamed(String name) {
        return new Dragon(name);
    }
    
    //groovy-style
    def makeDragonNamed(name) {
        new Dragon(name)
    }
    
    println(makeDragonNamed("Norbert"))
    
    void printSpaced(Object... objects) {
        for (Object o : objects) System.out.print(o + " ");
    }
    printSpaced("A", "B", "C"); // A B C
    println()
    printSpaced(1, 2, 3); // 1 2 3
    println()
