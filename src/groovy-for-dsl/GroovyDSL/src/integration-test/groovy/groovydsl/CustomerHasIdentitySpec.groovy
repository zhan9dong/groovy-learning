package groovydsl


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class CustomerHasIdentitySpec extends Specification {

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
}
