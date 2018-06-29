package com.com.dearle.groovydsl.chapters.ten

public class CustomersFactoryBuilder extends FactoryBuilderSupport {
    public CustomersFactoryBuilder(boolean init = true) {
        super(init)
    }

    def registerObjectFactories() {
	    registerFactory("customers", new CustomersFactory())
        registerFactory("customer", new CustomerFactory())
        registerFactory("invoice", new InvoiceFactory())
        registerFactory("sales_order", new SalesOrderFactory())
    }

}