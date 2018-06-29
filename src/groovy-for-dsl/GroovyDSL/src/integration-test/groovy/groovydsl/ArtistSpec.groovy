package groovydsl


import grails.test.mixin.integration.Integration
import grails.transaction.*
import spock.lang.*

@Integration
@Rollback
class ArtistSpec extends Specification {

    void "tesitng many-to-many relationships"() {
        given:
        def richWoman = new Song(title:"Rich Woman")
        def killingTheBlues = new Song(title:"Killing the Blues")

        def jimmyPage = new Artist(name:"Jimmy Page")
        def alisonKrauss = new Artist(name:"Alison Krauss")

        when:
        richWoman.addToArtists(jimmyPage)
        richWoman.addToArtists(alisonKrauss)
        jimmyPage.addToSongs(killingTheBlues)
        alisonKrauss.addToSongs(killingTheBlues)

        and:
        jimmyPage.save()
        alisonKrauss.save()

        then:
        ["Killing the Blues", "Rich Woman"] ==
          jimmyPage.songs.collect { it.title }.sort()
        ["Killing the Blues", "Rich Woman"] ==
          alisonKrauss.songs.collect { it.title }.sort()

        ["Alison Krauss", "Jimmy Page"] ==
          richWoman.artists.collect { it.name }.sort()
        ["Alison Krauss", "Jimmy Page"] ==
          killingTheBlues.artists.collect { it.name }.sort()
    }
}
