package listings.chap01

def number = 0
new File('data.txt').eachLine { line ->
    number++
    println "$number: $line"
}