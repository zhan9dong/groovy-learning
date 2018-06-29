def map = [a:'a',b:'b',c:[d:'d',e:['f','g']]]
assert map.c.e[1] == 'h'