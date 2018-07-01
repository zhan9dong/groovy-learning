
def test (x) {
    switch ( x ) {
    case "foo":
        result = "found foo"
        break
    case [4, 5, 6]:
        result = "4 5 or 6"
        break
    case 12..30: // Range
        result = "12 to 30"
        break
    case Integer:
        result = "was integer"
        break
    case Number:
        result = "was number"
        break
    default:
        result = "default"
    }
}

println test("foo")
println test(4)
println test(24)
println test(1337)
println test(3.141592)
println test(4.0)
