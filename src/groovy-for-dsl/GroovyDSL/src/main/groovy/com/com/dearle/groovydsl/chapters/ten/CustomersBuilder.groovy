package com.com.dearle.groovydsl.chapters.ten

import groovydsl.CustomerWithInvoices
import groovydsl.Invoice
import groovydsl.SalesOrder

class CustomersBuilder extends BuilderSupport {
  def createNode(name){
    Object result = null
    switch (name) {
      case "customer":
        return new CustomerWithInvoices(firstName:"", lastName:"")
      case "invoice":
        return new Invoice()
      case "salesOrder":
        return new SalesOrder(sku:"default",amount:1,price:0.0)
    }
  }
  def createNode(name, value){
    Object result = createNode(name)
    if (value instanceof CustomerWithInvoices && result instanceof Invoice)
      value.addToInvoices(result)
    if(value instanceof Invoice && result instanceof SalesOrder)
      value.addToOrders(result)
    return result
  }
  def createNode(name, Map attributes){
    Object result = null
    switch (name) {
      case "customer":
        return new CustomerWithInvoices(attributes)
      case "invoice":
        return new Invoice(attributes)
      case "salesOrder":
        return new SalesOrder(attributes)
    }
  }
  def createNode(name, Map attributes, value){
    Object result = createNode(name,attributes)
    if (value instanceof CustomerWithInvoices && result instanceof Invoice)
       value.addToInvoices(result)
    if(value instanceof Invoice && result instanceof SalesOrder)
       value.addToOrders(result)
    return result
  }
  void setParent(parent, child){
    if (child instanceof Invoice && parent instanceof CustomerWithInvoices)
      parent.addToInvoices(child)
    if (child instanceof SalesOrder && parent instanceof Invoice)
      parent.addToOrders(child)
  }
  void nodeCompleted(parent, node) {
    if (node != null)
       node.save()
  }
}
