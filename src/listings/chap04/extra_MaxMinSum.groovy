package listings.chap04

def kings = ['Dierk', 'Paul']
assert kings.max { item -> item.size() } == 'Dierk'
assert kings.min { item -> item.size() } == 'Paul'
assert kings.sum { item -> item.size() } == 9
