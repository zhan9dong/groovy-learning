Properties properties = new Properties()
File propertiesFile = new File('test.properties')
propertiesFile.withInputStream {
    properties.load(it)
}

println properties.name

println properties.url