import groovyx.gpars.actor.Actor
import groovyx.gpars.actor.DefaultActor

class Dragon extends DefaultActor {
    int age;

    void afterStart() {
        age = new Random().nextInt(1000) + 1;
    }

    void act() {
        loop {
            react {
                int num ->
                    if (num > age) {
                        reply 'too old'
                    } else if (num < age) {
                        reply "too young"
                    } else {
                        reply " you guessed right"
                        terminate()
                    }

            }
        }
    }

}

class Guesser extends DefaultActor {

    String name;

    Actor server;


    int myNum

    void act() {

        loop {
            myNum = new Random().nextInt(1000) + 1

            server.send myNum

            react {
                switch (it) {
                    case "too old":
                        println "$name: $myNum was too old";

                        break;
                    case "too young":
                        println "$name: $myNum was too young";
                        break;
                    default:
                        println "${name} I won ${myNum}";

                        terminate();
                        break;

                }
            }
        }
    }
}


def master = new Dragon().start()

def player = new Guesser(name: "Guesser", server: master).start()

[master, player]*.join()



