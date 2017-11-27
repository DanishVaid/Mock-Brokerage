
public class MarketAccount extends Account {

	public MarketAccount(int uniqueID) {
		super(uniqueID);
		// TODO Auto-generated constructor stub
	}
	
	public void deposit(double amount) {
		// TODO: SQL query, update customer balance
			
	}
	
	public void withdraw(double amount) {
		// TODO: SQL query, update customer balance
		
	}
	
	public void accrueInterest(double percent) {
		// TODO: SQL query, update customer balance
		// Initial thought: manager calls this function when they run add interest
		// Another possibility: delete this function and the manager updates the SQL tables
		// Thought: maybe the two functions are not the same thing.
		
	}
	
	public void buy(/* Idk what parameters */) {
		// TODO: Figure out how to buy
		
	}
	
	public void sell(/* Idk what parameters */) {
		// TODO: Figure out how to sell
	}

	public void getBalance() {
		// TODO: SQL query for balance attribute in table
		
	}
}
