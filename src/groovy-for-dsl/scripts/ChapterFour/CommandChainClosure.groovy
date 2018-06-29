def say (String message) {
    [ to: { person ->
        println "$message, $person!"
    }]
}

say "Hello" to "Fred"
// which is similar to calling
say("Goodbye")['to'].call('Barney')