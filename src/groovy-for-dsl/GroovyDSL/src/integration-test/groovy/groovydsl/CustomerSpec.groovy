package groovydsl

import com.com.dearle.groovydsl.chapters.ten.CustomersBuilder
import com.com.dearle.groovydsl.chapters.ten.CustomersFactoryBuilder
import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class CustomerSpec extends Specification {

    void "Gorm has added methods for persistence"() {
        given: "We save a domain object with save"
        def barney = new Customer(firstName: "Barney", lastName: "Rubble")
        barney.save()

        when: "we get it from the database"
        def fred = Customer.get(1)

        then:
        fred.firstName == "Barney"

        when:
        fred.firstName = "Freddie"
        fred.save()

        def customers = Customer.list()

        then:
        customers[0].firstName == 'Freddie'
    }

    void "belongsTo causes cascaded delete"() {
        given:
        def addr = new Address(street:"1 Rock Road", city:"Bedrock")
        def id = new Identity(email:"email", password:"password")
        def fred = new CustomerHasIdentity(firstName:"Fred",
            lastName:"Flintstone",
            address:addr,identity:id)

        addr.save(flush:true, failOnError: true)

        expect: "Only an address is saved"
        CustomerHasIdentity.count() == 0
        Address.count() == 1
        Identity.count() == 0

        when:
        fred.save(flush:true, failOnError: true)

        then: "Customer is save and save was cascaded to Identity"
        CustomerHasIdentity.count() == 1
        Address.count() == 1
        Identity.count() == 1

        when:
        fred.delete(flush:true, failOnError: true)

        then: "Customer deleted an delete was cascaded to identity"
        CustomerHasIdentity.count() == 0
        Address.count() == 1
        Identity.count() == 0

        when:
        addr.delete(flush:true, failOnError: true)

        then: "Now verything is deleted"
        CustomerHasIdentity.count() == 0
        Address.count() == 0
        Identity.count() == 0
    }

    def "belongsTo injects addTo methods to Domain class" () {
        given:
        def fred = new CustomerWithInvoices(firstName:"Fred", lastName:"Flintstone")
        fred.save(flush:true, failOnError: true)

        expect:
        CustomerWithInvoices.count() == 1
        Invoice.count() == 0
        SalesOrder.count() == 0

        when:
        def invoice = new Invoice()
        invoice.addToOrders(new SalesOrder(sku:"productid01",
                                           amount:1, price:1.00))
        invoice.addToOrders(new SalesOrder(sku:"productid02",
                                           amount:3, price:1.50))
        invoice.addToOrders(new SalesOrder(sku:"productid03",
                                           amount:2, price:5.00))

        then: "Invoice or sales orders are not yet persisted"
        CustomerWithInvoices.count() == 1
        Invoice.count() == 0
        SalesOrder.count() == 0

        when:
        fred.addToInvoices(invoice)
        fred.save(flush:true, failOnError: true)

        then: "Saving cascades to invoice and sales orders"
        CustomerWithInvoices.count() == 1
        Invoice.count() == 1
        SalesOrder.count() == 3

    }

    def "can build GORM objects with CustomersBuilder"() {
        given:
        def builder = new CustomersBuilder()

        def customers = builder.customers {
            def fred = customer(firstName:"Fred",lastName:"Flintstone") {
                invoice {
                    salesOrder(sku:"productid01", amount:1, price:1.00)
                    salesOrder(sku:"productid02", amount:2, price:1.00)
                    salesOrder(sku:"productid03", amount:3, price:1.00)
                }
            }
            def invoice2 = invoice(fred)

            salesOrder(invoice2, sku:"productid04", amount:1, price:1.00)
            salesOrder(invoice2, sku:"productid05", amount:1, price:1.00)
            salesOrder(invoice2, sku:"productid06", amount:1, price:1.00)
        }

        expect:
        CustomerWithInvoices.count() == 1
        Invoice.count() == 2
        SalesOrder.count() == 6
    }
    def "using a builder with FactoryBuilderSupport"() {
        given:
        def builder = new CustomersFactoryBuilder()

        def customers = builder.customers {
            fred = customer(firstName:"Fred",lastName:"Flintstone") {
                invoice {
                    sales_order(sku:"productid01", amount:1, price:1.00)
                    sales_order(sku:"productid02", amount:2, price:1.00)
                    sales_order(sku:"productid03", amount:3, price:1.00)
                }
            }
            invoice2 = invoice(fred)

            sales_order(invoice2, sku:"productid04", amount:1, price:1.00)
            sales_order(invoice2, sku:"productid05", amount:1, price:1.00)
            sales_order(invoice2, sku:"productid06", amount:1, price:1.00)
        }

        expect:
        CustomerWithInvoices.count() == 1
        Invoice.count() == 2
        SalesOrder.count() == 6

    }

}
