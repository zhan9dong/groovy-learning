def lst = [13, 12, 15, 14, 0, -1];
def newlst = [];
newlst = lst.collect {
    it * it
}

println newlst //[169, 144, 225, 196, 0, 1]

