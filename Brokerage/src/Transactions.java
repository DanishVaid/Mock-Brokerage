import java.sql.ResultSet;
import java.sql.SQLException;

public class Transactions {

	public static String getTransactionHistory() throws SQLException {
		String transactionHistory = "";
		
		String query = String.format("SELECT * FROM transactions WHERE tax_id = %d", User.currentTaxID);
		ResultSet result = JDBC.statement.executeQuery(query);
		
		while (result.next()) {
			// TODO: Get attributes.
			
			// TOOD: Store attributes into transactionHistory string.
			
		}
		return transactionHistory;
	}

	public static String generateMonthlyStatement(int taxID) {
		// TODO Auto-generated method stub
		// TODO: SQL query, given customer, generate list of all transactions in month
		// Should include name and email address of the customer
		// Also include initial and final balance, total earning/loss, total amount of commissions paid
		
		return null;
	}

	// Transaction history of all users
	// Should identify a transaction with date, accounts involved, stock symbol, number of shares, and price
	// Does not need to keep track of failed transactions
	
}
