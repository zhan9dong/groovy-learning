package groovydsl

/**
 * Created by fdearle on 05/04/2015.
 */
class Invoice {
    static hasMany = [orders:SalesOrder]
}
