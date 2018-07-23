def list = ['foo', 'bar']
def newList = []
list.collect(newList, {
    it.toUpperCase()
});

println newList