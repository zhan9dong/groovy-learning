package com.com.dearle.groovydsl.chapters.ten

import groovydsl.Invoice
import groovydsl.SalesOrder

public class SalesOrderFactory extends AbstractFactory {
  public boolean isLeaf() {
    return true
  }

  public Object newInstance(FactoryBuilderSupport builder, 
    Object name, Object value, Map attributes
    ) throws InstantiationException, IllegalAccessException {
      SalesOrder sales_order = null
      if (attributes != null)
        sales_order = new SalesOrder(attributes)
      else
        sales_order = new SalesOrder()
      if (value != null && value instanceof Invoice)
        value.addToOrders(sales_order)
      return sales_order
  }

  public void setParent(FactoryBuilderSupport builder, 
    Object parent, Object sales_order) {
	  if (parent != null && parent instanceof Invoice)
        parent.addToOrders(sales_order)
  }

  public void onNodeCompleted(FactoryBuilderSupport builder, 
    Object parent, Object sales_order) {
      sales_order.save()
  }
}
