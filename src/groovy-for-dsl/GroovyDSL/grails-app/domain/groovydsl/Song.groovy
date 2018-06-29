package groovydsl

class Song {
    String title
    static belongsTo = Artist
    static hasMany = [artists:Artist]
}
