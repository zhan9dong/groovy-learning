package listings.chap02

class Universe {
  @groovy.transform.TypeChecked
  int answer() { "forty two" }
}

println new Universe().answer()
