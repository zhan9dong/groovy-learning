def transfer( customer, from_account, to_account, amount) {
	println """debiting ${amount} from ${from_account} account,
crediting ${to_account} account for ${customer}"""
}

transfer("Joe Bloggs", "checking", "savings", 100.00)
