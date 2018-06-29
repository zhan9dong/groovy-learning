package com.dearle.groovydsl.chapters.eleven.BroadbandPlus

class BroadbandPlus {
	def rewards = new RewardService()
	
	def canConsume = { account, media ->
		def now = new Date()
			if (account.mediaList[media]?.after(now))
			return true
		
		account.points > media.points
	}
	def consume = { account, media ->
		// First consume add media to accounts access list
		if (account.mediaList[media.title] == null) {
			def now = new Date()
			account.points -= media.points
			account.mediaList[media] = now + media.daysAccess
			// Rewards only applied on first consumption
			rewards.applyRewardsOnConsume(account, media)
		}
	}
	def purchase = { account, media ->
		rewards.applyRewardsOnPurchase(account, media)
		account.points += media.points
		account.spend += media.points
	}
	def upgrade = { account, newPlan ->
		if (account.plan == "BASIC" && newPlan == "PLUS") 
			account.points += 130
		if (account.plan == "BASIC" && newPlan == "PREMIUM") 
			account.points += 430
		if (account.plan == "PLUS" && newPlan == "PREMIUM") 
			account.points += 300
		rewards.applyRewardsOnUpgrade(account, newPlan)		
		account.plan = newPlan
	}
	def extend = {account, media, days ->
		if (account.mediaList[media] != null)  {
			account.mediaList[media] += days 
		}
	}
}