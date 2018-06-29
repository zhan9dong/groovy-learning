package com.com.dearle.groovydsl.chapters.ten

public class CustomersFactory extends AbstractFactory {

  public boolean isLeaf() {
    return false
  }

  public Object newInstance(FactoryBuilderSupport builder, 
      Object name, Object value, Map attributes
  ) throws InstantiationException, IllegalAccessException {
     return name
  }

}
