package com.dearle.groovydsl.chapters.four

class Balance {
    String first
    String last
    BigDecimal balance
    String toString() { "$last, $first : $balance"}
}
