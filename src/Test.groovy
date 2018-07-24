import groovy.transform.Canonical

@Canonical
public class Person {
    String name
    String address
}
def person = new Person("richard","my address")

def person2 = new Person("richard","my address")

println person

println person.equals(person2)

println person2.hashCode() == person.hashCode()






