package com.dearle.groovydsl.chapters.seven

class BusinessService {
    static def remoteService
    static boolean isRemoteServiceLive() {
        remoteService.isLive()
    }
}
