package listings.chap01

InetAddress.getAllByName("google.com").collect {
    it.toString().split('/')[1]
}
