package com.dearle.groovydsl.chapters.seven

/**
 * Example methods used in Chapter 7
 */
class Methods {
    static def namedParamsMethod1(Map params) {
        assert params.a == 1
        assert params.b == 2
        assert params.c == 3
        true
    }

    static def namedParamsMethod2(Map params, String param2, String param3) {
        assert params.a == 1
        assert params.b == 2
        assert params.c == 3
        assert param2 == "param2"
        assert param3 == "param3"
        true
    }

    static String sentMessage
    static def sendMessage1(id, message) {
        sentMessage = "Sending (${message}) to ${id}"
    }

    static def sendMessage2(Map id, message) {
        sentMessage = "Sending (${message}) to ${id.to}"
    }

    enum Currency { USD, GBP, EUR }

    static def convert ( currency, amount) {
        def result
        switch (currency) {
        case Currency.USD: result = amount
            break
        case Currency.GBP: result = amount * 1.3
            break
        case Currency.EUR: result = amount * 1.1
        }
        result
    }

    static def deposit (double amount) {
        [
            currency: {  Currency currency ->
                [to: { account ->
                    account.balance += convert( currency , amount )
                    }
                ]

            }
        ]

    }
}
