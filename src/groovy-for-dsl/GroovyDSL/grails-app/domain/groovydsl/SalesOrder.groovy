package groovydsl

class SalesOrder {
    String sku
    int amount
    BigInteger price
    static belongsTo = [Invoice]
}
