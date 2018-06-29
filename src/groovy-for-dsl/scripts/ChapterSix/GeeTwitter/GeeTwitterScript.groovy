@Grab(group='org.twitter4j', module='twitter4j-core', version='[4.0,)')
import twitter4j.*
import static twitter4j.TwitterFactory.*

abstract class GeeTwitterScript extends Script {
	def sendMessage(user, message) {
		// Send a direct messsage to twitter user GroovyDSL
        singleton.sendDirectMessage(user, message)
	}
	// Method to apply a closure to each friend
	def eachMessage(Closure c) {
        singleton.directMessages.each {
	        c.call(it.senderScreenName,it.text)
		}
	}

	// Method to apply a closure to each friend
	def eachFriend(Closure c) {
        singleton.getFriendsList('groovydsl',-1).each {
	        c.call(it.screenName)
	    }
	}

	// Method to apply a closure to each follower
	def eachFollower(Closure c) {
        singleton.getFollowersList('groovydsl',-1).each {
	        c.call(it.screenName)
	    }
	}

	// Method to follow another twitter user
	def follow(user) {
        singleton.createFriendship(user)
	}

    void search(terms) {
   		def query = new Query(terms)
         singleton.search(query).tweets.each {
   			println it.text
   		}
   	}

	def search(terms, Closure c) {
		def query = new Query(terms)
        singleton.search(query).tweets.each {
			c.call(it.user.screenName,it.text)
		}
	}

    void search(Map args, String terms = "") {
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

	def block(user) {
        singleton.createBlock(user)
	}
}
