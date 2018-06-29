@Grab(group='org.twitter4j', module='twitter4j-core', version='[4.0,)')
import twitter4j.*
import static twitter4j.TwitterFactory.*

class GeeTwitter {
	static sendMessage(user, message) {
		// Send a direct messsage to twitter user GroovyDSL
		singleton.sendDirectMessage(user, message)
	}
	// Method to apply a closure to each friend
	static eachMessage(Closure c) {
        singleton.directMessages.each {
	        c.call(it.senderScreenName,it.text)
		}
	}

	// Method to apply a closure to each friend
	static void eachFriend(Closure c) {
        singleton.getFriendsList('fdearle',-1).each {
	        c.call(it.screenName)
	    }
	}

	// Method to apply a closure to each follower
	static void eachFollower(Closure c) {
        singleton.getFollowersList('fdearle',-1).each {
	        c.call(it.screenName)
	    }
	}

	// Method to follow another twitter user
	static void follow(user) {
        singleton.createFriendship(user)
	}

    static search(terms) {
   		def query = new Query(terms)
        def tweets = singleton.search(query).tweets
        def result = []
        tweets.each {
   			println it.text
            result << [from:it.user.name, tweet: it.text]
   		}
        result
   	}

	static void search(terms, Closure c) {
		def query = new Query(terms)
        singleton.search(query).tweets.each {
			c.call(it.user.screenName,it.text)
		}
	}

    static void search(Map args, String terms = "") {
        def queryString = ""
        args.each { arg ->
            switch (arg.key.toString().toLowerCase()) {
                case 'from':
                    queryString += "from:${arg.value} "
                    break
                case 'to':
                    queryString += "to:${arg.value} "
                    break
                case 'hashtag':
                    queryString += "#${arg.value} "
                    break
                case 'username':
                    queryString += "@${arg.value} "
                    break
            }
        }
        queryString += terms
        def query = new Query(queryString)
            singleton.search(query).tweets.each {
      			println it.text
      		}
    }

    static def block(user) {
           TwitterFactory.singleton.createBlock(user)
   	}
}
