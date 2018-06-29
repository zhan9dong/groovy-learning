package com.com.dearle.groovydsl.chapters.ten

import groovydsl.CustomerWithInvoices
import groovydsl.Invoice

public class InvoiceFactory extends AbstractFactory {

  public boolean isLeaf() {
    return false
  }

  public Object newInstance(FactoryBuilderSupport builder, 
    Object name, Object value, Map attributes
    ) throws InstantiationException, IllegalAccessException {
      Invoice invoice = null
      if (attributes != null)
        invoice = new Invoice(attributes)
      else
        invoice = new Invoice()
      if (value != null && value instanceof CustomerWithInvoices)
        value.addToInvoices(invoice)
      return invoice
  }

  public void setParent(FactoryBuilderSupport builder, 
    Object parent, Object invoice) {
      if (parent != null && parent instanceof CustomerWithInvoices)
        parent.addToInvoices(invoice)
  }

  public void onNodeCompleted(FactoryBuilderSupport builder, 
    Object parent, Object invoice) {
      invoice.save()
  }
}
