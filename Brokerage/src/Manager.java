import java.sql.ResultSet;
import java.sql.SQLException;

public class Manager extends User {

	/* COMMANDS */
	public static void addInterest(double interestRate) throws SQLException {
		MarketAccount.accrueInterestOnAllAccounts(interestRate);
		
		System.out.println("Successfully added interest to all market accounts.");
	}
	
	public static void generateMonthlyStatement(int taxID) throws SQLException {
		String monthlyStatement = Transactions.generateMonthlyStatement(taxID);
		
		System.out.println("Monthly report:");
		System.out.println(monthlyStatement);
	}
	
	public static void listActiveCustomers() throws SQLException {
		// Get transaction history for the month, group by user, add up shares, print name if count > 1000
		String query = "";
		ResultSet result = JDBC.statement.executeQuery(query);
	}
	
	// DTER: Government Drug & Tax Evasion Report
	public static void generateDTER() throws SQLException {
		// Get transaction history for the month, group by user, add up earnings, print name if count > 10000
		// This includes earnings from buying/selling and interest
		// The residence state of the customer should also be listed
		String query = "";
		ResultSet result = JDBC.statement.executeQuery(query);
	}
	
	public static void generateCustomerReport(int customerTaxID) throws SQLException {
		// TODO: SQL query, generate a list of all accounts associated with a customer
		// Include the current balance
		String query = "";
		ResultSet result = JDBC.statement.executeQuery(query);
	}
	
	public static void deleteTransactions() throws SQLException {
		// TODO: SQL query, delete list of transactions from each of the accounts
		String query = "";
		JDBC.statement.executeUpdate(query);
	}

}
