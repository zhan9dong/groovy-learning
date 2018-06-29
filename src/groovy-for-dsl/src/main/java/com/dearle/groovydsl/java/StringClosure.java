package com.dearle.groovydsl.java;

import groovy.lang.Closure;

public class StringClosure extends Closure{
    public StringClosure(Object owner) {
        super(owner);
    }

    public StringClosure(Object owner, Object thisObject) {

        super(owner, thisObject);
    }

    Object doCall(String message) {
        System.out.println(message);
        return null;
    }
}
