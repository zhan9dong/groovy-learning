@Grab(group='org.twitter4j', module='twitter4j-core', version='[4.0,)')
import twitter4j.*

// Method to follow another twitter user
void follow(user) {
    TwitterFactory.singleton.createFriendship(user)
}

void search(terms, Closure c) {
	def query = new Query(terms)
    TwitterFactory.singleton.search(query).tweets.each {
		c.call(it.user.screenName,it.text)
	}
}

// Print all recent tweets about Groovy and DSL
search ("Groovy DSL") { from, tweet ->
	println from + " : " + tweet
}

// Follow all users that have tweeted recently about Groovy and DSL
search ("Groovy DSL") { from, tweet ->
    follow(from)
    println "Following ${from}"
}
