@Grab(group='org.twitter4j', module='twitter4j-core', version='[4.0,)')
import twitter4j.*

followersList = { user ->
    TwitterFactory.singleton.getFollowersList(user,-1)
}
friendsList = { user ->
    TwitterFactory.singleton.getFriendsList(user,-1)
}
cachedFriendsList = friendsList.memoize()

// Method to apply a closure to each friend
def eachFriend(Closure c) {
    def friends = friendsList('groovydsl')
    friends.each {
        c.call(it.screenName)
    }
}

// Method to apply a closure to each follower
def eachFollower(Closure c) {
    def followers = followersList('groovydsl')
    followers.each { follower ->
        c.call(follower.screenName)
    }
}

// Method to follow another twitter user
void follow(user) {
	TwitterFactory.singleton.createFriendship(user   )
}

// Auto follow
eachFollower { follower ->
    def friends = cachedFriendsList('groovydsl')
	// If any of my friends is this follower
	if (friends.any { friend ->
		friend.screenName == follower
	})
		return;
	// Otherwise follow him
	println "Following ${follower}"
	follow follower
}

println "Now I'm following!"
eachFriend {
    println it
}
