import com.dearle.groovydsl.chapters.four.Account
import com.dearle.groovydsl.chapters.four.Customer

class AccountSample {
	public static void main (args) { 
       def customer = new Customer(id:1,name:"Aaron Anderson")
       def savings = new Account(id:2, balance:0.00, owner:customer)

       savings.credit 20.00
       println savings
    }
}
