
def a = {
    println "a this:" + this
    println "a owner:" + owner // ower指向b


    def b = {
        println "b this:" + this
        println "b owner:" + owner // ower指向b
    }
    b.call()


}

a.call()


