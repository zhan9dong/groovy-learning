def srcFile = new File("haiku.txt")
def targetFile = new File("testOutputStream.txt")
targetFile.withOutputStream {
    outputStream ->
        srcFile.withInputStream {
            inputStream ->
                outputStream << inputStream
        }
}


