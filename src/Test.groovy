class Person {
    String username
    String email
}

def persons = [
        new Person(username: 'mrhaki', email: 'email@host.com'),
        new Person(username: 'hubert', email: 'other@host.com')
]

def map = persons.inject([:]) { result, person ->
    result[person.username] = person.email
    result
}


println map
