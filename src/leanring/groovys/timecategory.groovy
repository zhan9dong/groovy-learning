    import groovy.time.TimeCategory    
    now = new Date()
    println now
    
    println now + 5
    
    use(TimeCategory) {
        nextWeekPlusTenHours = now + 1.week + 10.hours - 30.seconds
    }
    println nextWeekPlusTenHours
