import twitter4j.*

class GeeTwitter {
	static twitter = TwitterFactory.singleton
	
	static sendMessage(id, message) {
		// Send a direct messsage to twitter user GroovyDSL
		twitter.sendDirectMessage(id, message)
	}
	// Method to apply a closure to each friend
	static eachMessage(Closure c) {
		twitter.directMessages.each {
	        c.call(it.senderScreenName,it.text)
		}
	}

	// Method to apply a closure to each friend
	static void eachFriend(Closure c) {
	    twitter.friends.each {
	        c.call(it.screenName)
	    }
	}

	// Method to apply a closure to each follower
	static void eachFollower(Closure c) {
	    twitter.followers.each {
	        c.call(it.screenName)
	    }
	}

	// Method to follow another twitter user
	static void follow(user) {
		try {
			twitter.createFriendship(
				"${twitter.getUserDetail(user).getId()}")
		} catch (e) {}
	}

	static void search(terms, Closure c) {
		def query = new Query(terms)
		twitter.search(query).tweets.each {
			c.call(it.fromUser,tweet.text)
		}
	}
}
