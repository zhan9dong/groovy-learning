class myWizards {
    def list = [];
    String toString() {
        "${list}"
    }
    def setAt(it) {
        list.add(it)
    }
}

def wiz = new myWizards();

wiz['at'] = 'richardgong';

println wiz;



