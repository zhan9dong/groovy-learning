class CThread extends Thread {
    def Closure closure
    
    CThread( Closure c ) { 
        this.closure = c
        this.start()
    }
    public void run() {
    if (closure)
         closure() // invoke the closure
    }
    
}

CThread up = new CThread(
    {
        [1..9]*.each {
            sleep(10 * it) 
            println it
        }
    } )
    
CThread down = new CThread(
    {
     ["three","two", "one", "liftoff"].each {
            sleep(100) 
            println it
        }
    } )
