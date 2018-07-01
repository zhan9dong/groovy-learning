package listings.chap08

def boxer = new Expando()

boxer.takeThis  = 'ouch!'
boxer.fightBack = { times -> takeThis * times  }

assert boxer.fightBack(3) == 'ouch!ouch!ouch!'