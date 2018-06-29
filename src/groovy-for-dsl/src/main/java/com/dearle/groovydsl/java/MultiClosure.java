package com.dearle.groovydsl.java;

import groovy.lang.Closure;

public class MultiClosure extends Closure{
    public MultiClosure(Object owner) {
        super(owner);
    }

    public MultiClosure(Object owner, Object thisObject) {

        super(owner, thisObject);
    }

    Object doCall(String message) {
        System.out.println(message);
        return null;
    }

    Object doCall(Integer number) {
        System.out.println(number);
        return null;
    }
}
