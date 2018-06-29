import com.dearle.groovydsl.chapters.eleven.BroadbandPlus.Account
import com.dearle.groovydsl.chapters.eleven.BroadbandPlus.BroadbandPlus
import com.dearle.groovydsl.chapters.eleven.BroadbandPlus.Media
import com.dearle.groovydsl.chapters.eleven.BroadbandPlus.RewardService
import com.dearle.groovydsl.test.SpockScriptSpecification

class ChapterElevenSpec extends SpockScriptSpecification {

    def "Run Binding 1 example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Binding1.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Success" == output()
    }

    def "Run Binding 2 example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Binding2.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Success" == output()
    }

    def "Run Binding 3 example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Binding3.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Success" == output()
    }

    def "Run Binding 4 example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Binding4.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Hello, World!" == output()
    }

    def "Run Binding 5 example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Binding5.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Hello, World!" == output()
    }

    def "Run Nested block example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Nested.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """block encountered
nested block encountered
block encountered
nested block encountered""" == output()
    }

    def "Run Nested block with spec example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/NestedWithSpec.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """first block encountered
first nested encountered
second block encountered
second nested encountered""" == output()
    }

    def "Run Singleton example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Singleton.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Success" == output()
    }

    def "Run Context example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Context.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            "Success" == output()
    }

    def "Run Results example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Results.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """innerBlock: Hello, World!
outerBlock: Hello, World!
caller: Hello, World!""" == output()
    }

    def "Run Deferred execution example" () {
        given: "we have a script to run"
            def script = new File("scripts/ChapterEleven/Deferred.groovy")

        when: "we run the script"
            shell.evaluate script

        then:
            """saved
deferred""" == output()
    }

    def account
    def up
    def terminator
    def halo3
    def halo1
    def bbPlus

    def setup() {
        account = new Account(plan:"BASIC", points:120, spend:0.0)
        up = new Media(title:"UP", type:"VIDEO", newRelease:true,
                      price:3.99, points:40, daysAccess:1,
                      publisher:"Disney")
        terminator = new Media(title:"Terminator", type:"VIDEO",
                      newRelease:false, price:2.99, points:30,
                      daysAccess:1, publisher:"Fox")
        halo3 = new Media(title:"Halo III", type:"GAME",
                      newRelease:true, price:2.99, points:30,
                      daysAccess:3, publisher:"Microsoft")
        halo1 = new Media(title:"Halo", type:"GAME",
                      newRelease:false, price:1.99, points:20,
                      daysAccess:3,publisher:"Microsoft")
        bbPlus = new BroadbandPlus()
        RewardService.loadRewardRules()

    }
    def "Disney Reward programme is applied"() {
        expect:
        bbPlus.canConsume(account, up)
        account.points == 120

        when:
        def expected = account.points - up.points + up.points / 4
        bbPlus.consume(account, up)

        then:
        account.points == expected

        when:
        bbPlus.consume(account, terminator)

        then:
        account.points == expected - terminator.points
    }
    def "Rental extension reward"() {
        given:
        bbPlus.consume(account, up)
        bbPlus.consume(account, terminator)
        def now = new Date()
        expect: "Extension applied to Up but not Terminator"
        account.getMediaExpiry(up).after(now + 1)
        account.getMediaExpiry(
                terminator).after(now + 1) == false
    }
    def "Purchase reward applied to Games"() {
        expect:
        account.points == 120

        when:
        bbPlus.purchase(account,  terminator)
        bbPlus.consume(account,  terminator)
        then:
        account.points == 120
        when:
        bbPlus.purchase(account,  halo1)
        bbPlus.consume(account,  halo1)
        then:
        account.points == 122

    }

    def "Upgrade to plus reward"() {
        expect:
        account.points == 120

        when:
        bbPlus.upgrade(account,  "PLUS")
        then: "Should have 250 for PLUS and 100 bonus"
        account.points == 350

        when:
        bbPlus.upgrade(account,  "PREMIUM")
        then: "Should have 550 for PREMIUM and 100 bonus"
        account.points == 650
    }

    def "Upgrade to premium reward"() {
        expect:
        account.points == 120

        when:
        bbPlus.upgrade(account,  "PREMIUM")
        then: "Should have 550 for PREMIUM and 100 bonus"
        account.points == 650
    }
}
