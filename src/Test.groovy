def list = new ObservableList()
def printer = { e -> println e.class }
list.addPropertyChangeListener(printer)
list.add 'Harry Potter'
list.add 'Hermione Granger'
list.remove(0)