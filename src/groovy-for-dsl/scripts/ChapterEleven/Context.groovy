class Account  {
    double spend = 11.00
    double balance = 100.00
    boolean active = true
    
    void credit (double value) {
        balance += value
    }
}
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
def account = new Account()
binding.account = account
binding.monthSpend = account.spend
binding.credit = account.&credit

assert account.balance == 100.00

def shell = new GroovyShell(binding)
shell.evaluate(
"""    reward {
          apply {
            if (account.active && monthSpend > 10.00)
                credit 5.00
          }
    }
"""
)

assert account.balance == 105.00

println "Success"
