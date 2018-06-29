binding.block = { spec, closure ->
    def cloned = closure.clone()
    cloned.delegate = delegate
    this.enclosing = "block"
    
    println "${spec} encountered"
    cloned()
}

binding.nestedBlock = { spec, closure ->
    assert closure.delegate.enclosing == "block"
    def cloned = closure.clone()
    cloned.delegate = delegate
    this.enclosing = "nestedBlock"
    
    println "${spec} encountered"
    cloned()
}

block ("first block") {
  nestedBlock ("first nested"){   
  }
}
block "second block", {
    nestedBlock ("second nested"){   
    }
}
