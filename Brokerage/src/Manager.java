import java.sql.SQLException;

public class Manager extends User {

	/* COMMANDS */
	public static void addInterest(double interestRate) throws SQLException {
		// TODO: SQL query, add interest to all market accounts
		String query = "";
		JDBC.statement.executeUpdate(query);
	}
	
	public static void generateMonthlyStatement(int taxID) throws SQLException {
		// TODO: SQL query, given customer, generate list of all transactions in month
		// Should include name and email address of the customer
		// Also include initial and final balance, total earning/loss, total amount of commissions paid
		String query = "";
		JDBC.statement.executeQuery(query);
	}
	
	public static void listActiveCustomers() throws SQLException {
		// TODO: SQL query, list all customers who have traded at least 1000 shares in the current month
		String query = "";
		JDBC.statement.executeQuery(query);
	}
	
	// DTER: Government Drug & Tax Evasion Report
	public static void generateDTER() throws SQLException {
		// TODO: SQL query, list all customers who have earned more than 10,000 within one month
		// This includes earnings from buying/selling and interest
		// The residence state of the customer should also be listed
		String query = "";
		JDBC.statement.executeQuery(query);
	}
	
	public static void generateCustomerReport(int customerTaxID) throws SQLException {
		// TODO: SQL query, generate a list of all accounts associated with a customer
		// Include the current balance
		String query = "";
		JDBC.statement.executeQuery(query);
	}
	
	public static void deleteTransactions() throws SQLException {
		// TODO: SQL query, delete list of transactions from each of the accounts
		String query = "";
		JDBC.statement.executeUpdate(query);
	}

}
