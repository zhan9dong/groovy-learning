def binding = new Binding()
binding.reward = { closure ->
	closure.delegate = delegate
	closure()
}

binding.apply = { closure ->
	closure.delegate = delegate
	closure()
}

// lookup account in binding
binding.account = Account.get()
binding.monthSpend = account.calculateMonthSpendToDate()
binding.credit = Account.&credit

def shell = new GroovyShell(binding)
shell.evaluate(
"""	reward {
  		apply {
			if (account.active && monthSpend >100.00)
				credit 5.00
  		}
	}
"""
)
