import groovy.transform.Immutable


@Immutable
public class A{
    String name;
    int size;
}

A a = new A("richard", 100);

println a;
