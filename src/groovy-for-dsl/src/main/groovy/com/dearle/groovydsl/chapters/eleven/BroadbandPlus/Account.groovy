package com.dearle.groovydsl.chapters.eleven.BroadbandPlus

class Account {
	String subscriber
	String plan
	int points
	double spend
	Map mediaList = [:]
	void addMedia (media, expiry) {
		mediaList[media] = expiry
	}
	void extendMedia(media, length) {
		mediaList[media] += length 
	}
	Date getMediaExpiry(media) {
		if(mediaList[media] != null) {
			return mediaList[media]
		}
	}
}