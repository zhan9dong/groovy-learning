@Grab(group='org.twitter4j', module='twitter4j-core', version='[4.0,)')
import twitter4j.*

// Get a twitter connection
def twitter = TwitterFactory.singleton

// Update twitter status
twitter.updateStatus("Updating my status via the Twitter4J APIS")

println twitter.showUser('fdearle').status.text
