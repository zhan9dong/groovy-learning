import groovy.transform.*
@Canonical
class Vampire {String name; int yearBorn; }
ARRAYS: {
    Vampire[] vampires = new Vampire[10]; // Vampire array with length 10
    String[] names = ["Dracula", "Edward"];
    println(vampires)
    println(names)
}
LISTS: {
	// java way
	List<Vampire> vampires = new ArrayList<>();
	vampires.add(new Vampire("Count Dracula", 1897));
	println(vampires)

	//groovy way
	def list = []
	list.add(new Vampire("Count Dracula", 1897))
	// or
	list << new Vampire("Count Dracula", 1897)
	// or
	list += new Vampire("Count Dracula", 1897)
}
SET1: {
    Set<String> dragons = new HashSet<>();
    dragons.add("Lambton");
    dragons.add("Deerhurst");
    println dragons.size(); // 2
    dragons.remove("Lambton");
    println dragons.size(); // 1
    println(dragons)
}
SET2: {
    SortedSet<String> dragons = new TreeSet<>();
    dragons.add("Lambton");
    dragons.add("Smaug");
    dragons.add("Deerhurst");
    dragons.add("Norbert");
    System.out.println(dragons);
}
MAPS: {
    // [Deerhurst, Lambton, Norbert, Smaug]
    Map<String,String> map = new HashMap<>();
    map.put("Smaug", "deadly");
    map.put("Norbert", "cute");
    map.size(); // 2
    map.get("Smaug"); // deadly    
}
