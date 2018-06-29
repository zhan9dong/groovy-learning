def transfer( transaction, amount) {
    println """debiting ${amount} from ${transaction.from} account,
crediting ${transaction.to} for ${transaction.for}"""
}

transfer 100.00, from: "checking", to: "savings", for: "Joe Bloggs"
transfer for: "Joe Bloggs", 200.00, from: "checking", to: "savings"
