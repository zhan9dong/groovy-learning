def person = new Expando()

person.name = 'Alice'

person.age = 18


person.description = {

    println """
           ----------description---------

               name: ${delegate.name}

              age:  ${delegate.age}
           ------------------------------
         """
}



person.description()

