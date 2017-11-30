
public class Manager extends User {

	/* COMMANDS */
	public void addInterest() {
		// TODO: SQL query, add interest to all market accounts
		
	}
	
	public void getMonthlyStatement(String customerID) {
		// TODO: SQL query, given customer, generate list of all transactions in month
		// Should include name and email address of the customer
		// Also include initial and final balance, total earning/loss, total amount of commissions paid
		
	}
	
	public void getActiveCustomers() {
		// TODO: SQL query, list all customers who have traded at least 1000 shares in the current month
		
	}
	
	// DTER: Government Drug & Tax Evasion Report
	public void getDTER() {
		// TODO: SQL query, list all customers who have earned more than 10,000 within one month
		// This includes earnsing from buying/selling and interest
		// The residence state of the customer should also be listed
		
	}
	
	public void getCustomerReport(String customerID) {
		// TODO: SQL query, generate a list of all accounts associated with a customer
		// Include the current balance
		
	}
	
	public void deleteTransactions() {
		// TODO: SQL query, delete list of transactions from each of the accounts
		
	}

}
