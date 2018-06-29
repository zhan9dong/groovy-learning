class Clazz {
    def method() {
        this
    }

    def methodDelegate() {
        delegate
    }

    def methodOwner() {
        owner
    }
    def closure = {
        [this, delegate, owner]
    }

    void closureWithinMethod() {
        def methodClosure = {
            [this, delegate, owner]
        }
        methodClosure()
    }
}

def clazz = new Clazz()

def methodThis = clazz.method()

assert methodThis == clazz

def (closureThis, closureDelegate, closureOwner) = clazz.closure()

assert closureThis == clazz
assert closureDelegate == clazz
assert closureOwner == clazz

def (closureInMethodThis, closureInMethodDelegate, closureInMethodOwner) = clazz.closure()

assert closureInMethodThis == clazz
assert closureInMethodDelegate == clazz
assert closureInMethodOwner == clazz
assert closureInMethodThis != clazz.closure
assert closureInMethodDelegate != clazz.closure
assert closureInMethodOwner != clazz.closure
