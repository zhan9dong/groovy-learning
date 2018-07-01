package listings.chap01

def classes = [String, List, File]
for (clazz in classes) {
    println clazz.package.name
}