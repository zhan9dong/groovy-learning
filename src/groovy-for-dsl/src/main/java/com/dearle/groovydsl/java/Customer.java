package com.dearle.groovydsl.java;

public class Customer implements java.io.Serializable {
    private int id;
    private String name;

    public Customer () {
    }
    public int getId() {
        return this.id;
    }
    public void setId(final int id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(final String name) {
        this.name = name;
    }
}
