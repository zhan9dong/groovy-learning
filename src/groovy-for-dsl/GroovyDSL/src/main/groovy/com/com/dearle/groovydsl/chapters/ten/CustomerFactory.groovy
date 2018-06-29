package com.com.dearle.groovydsl.chapters.ten

import groovydsl.CustomerWithInvoices

public class CustomerFactory extends AbstractFactory {

  public boolean isLeaf() {
    return false
  }

  public Object newInstance(FactoryBuilderSupport builder, 
	Object name, Object value, Map attributes
	) throws InstantiationException, IllegalAccessException {
      CustomerWithInvoices customer = null
      if (attributes != null)
        customer = new CustomerWithInvoices(attributes)
      else
        customer = new CustomerWithInvoices()
      return customer
  }

  public void onNodeCompleted(FactoryBuilderSupport builder, 
	Object parent, Object customer) {
      customer.save()
  }
}
