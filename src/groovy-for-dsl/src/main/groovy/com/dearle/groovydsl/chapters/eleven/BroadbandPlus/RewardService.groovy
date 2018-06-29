package com.dearle.groovydsl.chapters.eleven.BroadbandPlus

class RewardService {
	static boolean onConsumeProvided = true
	def static onConsume = {
		onPonsumeProvided = false
	}
	static boolean onPurchaseProvided = true
	def static onPurchase = {
		onPurchaseProvided = false
	}
	static boolean onUpgradeProvided = true
	def static onUpgrade = {	
		onUpgradeProvided = false
	}

	void prepareClosures (Binding binding) {
		binding.onConsume = onConsume
		binding.onPurchase = onPurchase
		binding.onUpgrade = onUpgrade
		binding.reward = { spec, closure ->
    		closure.delegate = delegate
    		binding.result = true
    		binding.and = true 
    		closure()
		}
		binding.condition = { closure ->
			closure.delegate = delegate

			if (binding.and)
				binding.result = (closure() && binding.result) 
			else
				binding.result = (closure() || binding.result) 		
		}

		binding.allOf = { closure ->
			closure.delegate = delegate
			def storeResult = binding.result
			def storeAnd = binding.and
	    	binding.result = true // Starting premise is true
    		binding.and = true 

			closure()
	
			if (storeAnd) {
				binding.result = (storeResult && binding.result)
			} else {
				binding.result = (storeResult || binding.result)
			}
			binding.and = storeAnd
		}

	 	binding.anyOf = { closure ->
	    	closure.delegate = delegate
			def storeResult = binding.result
			def storeAnd = binding.and
    
		    binding.result = false // Starting premise is false
		    binding.and = false 

		    closure()    
			if (storeAnd) {
				binding.result = (storeResult && binding.result)
			} else {
				binding.result = (storeResult || binding.result)
			}
			binding.and = storeAnd
		}

		binding.grant = { closure ->
    		closure.delegate = delegate
    
	    	if (binding.result)
    	    	closure()    
		}
		binding.extend = { days ->
			def bbPlus = new BroadbandPlus()
			bbPlus.extend( binding.account, binding.media, days)
		}
		binding.points = { points ->
			binding.account.points += points
		}
	}
	void prepareMedia(binding, media) {
		binding.media = media
		binding.isNewRelease = media.newRelease
		if (media.type == "VIDEO")
			binding.isVideo = true
		else
			binding.isVideo = false
		if (media.type == "GAME")
			binding.isGame = true
		else
			binding.isGame = false
		if (media.type == "SONG")
			binding.isSong = true
		else
			binding.isSong = false
		
	}
	static void loadRewardRules() {
		Binding binding = new Binding()
			
		binding.onConsume = onConsume
		binding.onPurchase = onPurchase
		binding.onUpgrade = onUpgrade
			
		GroovyShell shell = new GroovyShell(binding)
		shell.evaluate(new File("src/main/groovy/com/dearle/groovydsl/chapters/eleven/BroadBandPlus/offers/Offers.groovy"))
		
		onConsume = binding.onConsume
		onPurchase = binding.onPurchase
		onUpgrade = binding.onUpgrade	
	}
	void applyRewardsOnConsume(account, media) {
		if (onConsumeProvided) {
			Binding binding = new Binding()
			binding.account = account
			prepareClosures(binding)
			prepareMedia(binding, media)
			
			GroovyShell shell = new GroovyShell(binding)
			shell.evaluate("onConsume.delegate = this;onConsume()")
		}
	}
	void applyRewardsOnPurchase(account, media) {
		if (onPurchaseProvided) {
			Binding binding = new Binding()
			binding.account = account
			prepareClosures(binding)
			prepareMedia(binding, media)
			
			GroovyShell shell = new GroovyShell(binding)
			shell.evaluate("onPurchase.delegate = this;onPurchase()")
		}
	}
	void applyRewardsOnUpgrade(account, plan) {
		if (onUpgradeProvided) {
			Binding binding = new Binding()
			binding.account = account
			binding.toPlan = plan
			binding.fromPlan = account.plan
			prepareClosures(binding)
			
			println "FROM ${binding.fromPlan} TO ${binding.toPlan}" 
			GroovyShell shell = new GroovyShell(binding)
			shell.evaluate("onUpgrade.delegate = this;onUpgrade()")
		}
	}
}