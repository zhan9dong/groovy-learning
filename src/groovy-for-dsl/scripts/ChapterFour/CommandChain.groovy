class Message {
    String message

    def to( String person) {
        println "$message, $person!"
    }
}

def say (String message) {
    new Message(message:message)
}

say "Hello" to "Fred"
//which is similar to calling
say("Goodbye").to("Barney")