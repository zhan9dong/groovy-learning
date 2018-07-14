public class SMS {
    def from(String a) {
        println("from ${a}")
    }

    def to(String a) {
        println("to ${a}")
    }

    def body(String a) {
        println("body ${a}")
    }
    def static bodyDSL(String a){
        println "bodyDSL ...${a}"
    }
    def send() {
        println("send ...");
    }

    def static send(block) {
        def sms = new SMS();
        block.delegate = sms;
        block()
        sms.send()
    }
}

SMS.send {
    from "richard"
    to " to richard"
    body "hi richard.g"
}