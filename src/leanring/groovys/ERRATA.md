
# GPars

Although GPars was bundled into Groovy after 2.1, they apparently removed it in later versions so you will still need to add a dependency.

For example, using the built-in _Grape_ system, you can simply add the following to the top of a Groovy script:

    @Grab(group='org.codehaus.gpars', module='gpars', version='1.2.1')

Or, using Gradle:
    
    dependencies {
        compile "org.codehaus.gpars:gpars:1.2.1"
    }

See [GPars website](http://www.gpars.org/webapp/Integration.html) for more information.


