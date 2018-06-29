package groovydsl

class CustomerWithInvoices {
    String firstName
    String lastName
    static hasMany = [invoices:Invoice]
}
