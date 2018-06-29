binding.block = { closure ->
    def cloned = closure.clone()
    cloned.delegate = delegate
    this.enclosing = "block"
    
    println "block encountered"
    cloned()
}

binding.nestedBlock = { closure ->
    assert closure.delegate.enclosing == "block"
    def cloned = closure.clone()
    cloned.delegate = delegate
    this.enclosing = "nestedBlock"
    
    println "nested block encountered"
    cloned()
}

block {
  nestedBlock { 
  }
}
block {
  nestedBlock { 
  }
}

