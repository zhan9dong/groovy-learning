package com.dearle.groovydsl.chapters.four

class Account {
	int id
	double balance
	Customer owner
	void credit (double deposit) {
		balance += deposit
	}
	String toString() {
		"Account id " + id + " owner " + owner.name + " balance is " + balance
	}
}
