package com.dearle.groovydsl.chapters.eleven.BroadbandPlus

class TestConsume {
def account
def up
def terminator
def halo3
def halo1
def bbPlus
	
void setUp() {
  account = new Account(plan:"BASIC", points:120, spend:0.0)
  up = new Media(title:"UP", type:"VIDEO", newRelease:true, 
                price:3.99, points:40, daysAccess:1, 
                publisher:"Disney")
  terminator = new Media(title:"Terminator", type:"VIDEO", 
                newRelease:false, price:2.99, points:30, 
                daysAccess:1, publisher:"Fox")
  halo3 = new Media(title:"Halo III", type:"GAME",
                newRelease:true, price:2.99, points:30, 
                daysAccess:3, publisher:"Microsoft")
  halo1 = new Media(title:"Halo", type:"GAME", 
                newRelease:false, price:1.99, points:20, 
                daysAccess:3,publisher:"Microsoft")
  bbPlus = new BroadbandPlus()
  RewardService.loadRewardRules()
}
void testDisneyReward() {
  assert bbPlus.canConsume( account, up)
  assert account.points == 120
  def expected = account.points - up.points + up.points/4
  bbPlus.consume(account, up)
  assert account.points == expected
  bbPlus.consume(account, terminator)
  assert account.points == expected - terminator.points
}
void testExtensionReward() {
  bbPlus.consume(account, up)
  bbPlus.consume(account, terminator)
  def now = new Date()
  // Extension applied to Up but not Terminator
  assert account.getMediaExpiry(up).after(now + 1)
  assert account.getMediaExpiry(
	                    terminator).after(now + 1) == false
}
void testPurchaseRewardOnGames() {
  assert account.points == 120
  bbPlus.purchase(account,  terminator)
  bbPlus.consume(account,  terminator)
  assert account.points == 120	
  bbPlus.purchase(account,  halo1)
  bbPlus.consume(account,  halo1)
  assert account.points == 122
}
void testUpgradeToPlusReward() {
  assert account.points == 120
  bbPlus.upgrade(account,  "PLUS")
  // Should have 250 for PLUS and 100 bonus
  assert account.points == 350	
  bbPlus.upgrade(account,  "PREMIUM")
  // Should have 550 for PREMIUM and 100 bonus 
  // from the previous upgrade
  println account.points
  assert account.points == 650
}
void testUpgradeToPremiumReward() {
  assert account.points == 120
  bbPlus.upgrade(account,  "PREMIUM")
  // Should have 550 for PREMIUM and 100 bonus
  println account.points
  assert account.points == 650
}
}
