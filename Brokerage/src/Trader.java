
public class Trader extends User {
	
	
	
	public Trader(String username, String password) {
		super(username, password);
	}
	
	/* COMMANDS */
	public void deposit() {
		// TODO: Into market or stock account? Or is there one balance?
		// Probably market, stock balance is different, not money but amount of stocks
		
	}
	
	public void withdraw() {
		// TODO: From market or stock account? Or is there one balance?
		// Probably market, stock balance is different, not money but amount of stocks
		
	}
	
	public void buy() {
		// TODO: Figure out what to do
		// Initial thought: Subtract money amount from market account, and put stock into stock account
		
	}
	
	public void sell() {
		// TODO: Figure out what to do
		// Initial thought: Add money amount from market account, and take stock out from stock account
		
	}
	
	public void getBalance() {
		// TODO: SQL query for balance attribute
		
	}
	
	public void showTransactionHistory() {
		// TODO: SQL query for transaction history for owner's stock account
		
	}

	public void getStockPrice(String stockSymbol, String actor) {
		// TODO: SQL query for stock price, using stock symbol and actor
		
	}
	
	public void getMovieInfo(String movieName) {
		// TODO: Get movie info from external system, using (not sure) movie name
		
		// TODO: Should also be able to get "Top Movies"
		// Given a specific time interval (of release date), list the titles with 5 stars
		
		// TODO: Display all reviews for for a given movie
		
	}

}
