package listings.chap06

def mac() {
    if (System.properties.'os.name'.contains('Mac'))
        "We're on Mac." // no 'return'
    else
        "Oh, well ..."  // no 'return'
}
println mac()