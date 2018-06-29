package groovydsl

import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Customer)
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
}
