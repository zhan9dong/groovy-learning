def customer = new Expando()

customer.id = 1001
customer.firstName = "Fred"
customer.surname = "Flintstone"
customer.street = "1 Rock Road"

customer.prettyPrint = {
    println "Customer has following properties"  
    customer.properties.sort {it.key} .each {
        if (it.key != 'prettyPrint')
            println "    " + it.key + ": " + it.value 
    } 
}

customer.prettyPrint()
