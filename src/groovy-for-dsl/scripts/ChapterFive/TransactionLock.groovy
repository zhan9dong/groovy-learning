class Customer {
    String name
}
def locked (Closure c) {
    println "Transaction lock"
    c.call()
    println "Transaction release"
}

def update (customer, Closure c) {
    println "Customer name was ${customer.name}"
    c.call(customer)
    println "Customer name is now ${customer.name}"
}

def customer = new Customer(name: "Fred")

locked {
    update(customer) { cust ->
        cust.name = "Barney"
    }
}