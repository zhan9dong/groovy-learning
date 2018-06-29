package groovydsl

class Artist {
    String name
    static hasMany = [songs:Song]
}
