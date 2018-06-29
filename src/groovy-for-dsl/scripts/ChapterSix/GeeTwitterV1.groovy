@Grab(group='org.twitter4j', module='twitter4j-core', version='[4.0,)')
import twitter4j.*

class GeeTwitter {
    def twitter = TwitterFactory.singleton

    void search(terms, Closure c) {
        def query = new Query(terms)
        twitter.search(query).tweets.each {
            c.call(it.user.screenName,it.text)
        }
    }
}

GeeTwitter gTwitter = new GeeTwitter()

gTwitter.search ("Groovy DSL") { from, tweet ->
    println "${from} : ${tweet}"
}

