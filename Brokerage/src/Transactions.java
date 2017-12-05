import java.sql.ResultSet;
import java.sql.SQLException;

import javax.activation.CommandInfo;

public class Transactions {

	public static ResultSet getTransactionHistory() throws SQLException {
		
		return null;
	}

	public static String generateMonthlyStatement(int taxID) throws SQLException {
		// TODO: Test
		String monthlyStatement;

		// Getting name and email
		String query = String.format("SELECT name, email FROM customer_profiles WHERE tax_id = %d;", taxID);
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		monthlyStatement = String.format("Customer Name: %s\nCustomer Email: %s\nTransaction History:\n", result.getString("name"), result.getString("email"));

		// Getting TXN history
		monthlyStatement += Transactions.showTransactionHistory(taxID);

		String sub_query;
		// Getting initial account balance
		sub_query = String.format("SELECT MIN(day) FROM daily_balances WHERE month = %d AND tax_id = %d", CommandUI.currentDate.getMonth(), taxID);
		query = String.format("SELECT balance FROM daily_balances WHERE month = %d AND day = (%s) AND tax_id = %d", CommandUI.currentDate.getMonth(), sub_query, taxID);
		result = JDBC.statement.executeQuery(query);
		result.first();
		double initialBalance = result.getDouble("balance");
		monthlyStatement += String.format("Initial Balance: $%.2f\n", initialBalance);

		// Getting final account balance
		sub_query = String.format("SELECT MAX(day) FROM daily_balances WHERE month = %d AND tax_id = %d", CommandUI.currentDate.getMonth(), taxID);
		query = String.format("SELECT balance FROM daily_balances WHERE month = %d AND day = (%s) AND tax_id = %d", CommandUI.currentDate.getMonth(), sub_query, taxID);
		result = JDBC.statement.executeQuery(query);
		result.first();
		double finalBalance = result.getDouble("balance");
		monthlyStatement += String.format("Final Balance: $%.2f\n", finalBalance);

		// Earnings/Losses
		monthlyStatement += String.format("Total Earnings (thus far): $%.2f\n", (finalBalance - initialBalance));
		

		// Total commissions paid
		query = String.format("SELECT COUNT(*) FROM transactions WHERE tax_id = %d AND month = %d AND(txn_type = 'buy' OR txn_type = 'sell');", taxID, CommandUI.currentDate.getMonth());
		result = JDBC.statement.executeQuery(query);
		result.first();
		double commissions = 20.00 * result.getInt(1);  // FIXME: this might fuck up
		monthlyStatement += String.format("Total Commission Paid This Month: $%.2f\n", commissions);

		return monthlyStatement;
	}

	public static String showTransactionHistory() throws SQLException {
		String transactionHistory = String.format("%5s %-8s %-10s %-10s %s\n", "", "Tax ID", "Date", "Type", "Details");
		
		String query = String.format("SELECT * FROM transactions WHERE tax_id = %d", User.currentTaxID);
		ResultSet result = JDBC.statement.executeQuery(query);

		while (result.next()) {
			String attributes = String.format("%5s %-8d %-10s %-10s %s\n", "", result.getInt("tax_id"), result.getString("date"), result.getString("txn_type"), result.getString("txn_details"));
			transactionHistory += attributes;
		}
		return transactionHistory;
	}


	public static String showTransactionHistory(int tax_id) throws SQLException {
		String transactionHistory = String.format("%5s %-8s %-10s %-10s %s\n", "", "Tax ID", "Date", "Type", "Details");
		
		String query = String.format("SELECT * FROM transactions WHERE tax_id = %d", tax_id);
		ResultSet result = JDBC.statement.executeQuery(query);

		while (result.next()) {
			String attributes = String.format("%5s %-8d %-10s %-10s %s\n", "", result.getInt("tax_id"), result.getString("date"), result.getString("txn_type"), result.getString("txn_details"));
			transactionHistory += attributes;
		}
		return transactionHistory;
	}

	public static boolean addInterestRecord(int tax_id, double interest) throws SQLException {
		String notes = String.format("Interest Amount Given: %.2f", interest);
		String query = String.format("INSERT INTO transactions(tax_id, date, month, txn_type, txn_details) VALUES (%d, '%s', %d, 'interest', '%s');", tax_id, CommandUI.currentDate.toString(), CommandUI.currentDate.getMonth(), notes);

		if ((JDBC.statement.executeUpdate(query)) == 1){
			return true;
		}
		return false;
	}

	public static double getInterestGiven(int tax_id) throws SQLException {
		String query = String.format("SELECT txn_details FROM transactions WHERE tax_id = %d AND month = %d", tax_id, CommandUI.currentDate.getMonth());
		ResultSet results = JDBC.statement.executeQuery(query);
		results.first();
		String[] detail_sections = results.getString("txn_details").split(" ");
		return Double.parseDouble(detail_sections[detail_sections.length - 1]);
	}

	//TODO: Ensure its added to all deposit calls
	public static boolean addDepositRecord(double amount) throws SQLException {
		String note = String.format("Deposited $%.2f to account.", amount);
		return insertRecord(note, "deposit");
	}

	//TODO: Ensure its added to all withdraw calls
	public static boolean addWithdrawRecord(double amount) throws SQLException {
		String note = String.format("Withdrew $%.2f from account.", amount);
		return insertRecord(note, "withdraw");
	}

	//TODO: Ensure its added to all buy command
	public static boolean addBuyRecord(String stockSym, double stockPrice, double numOfShares) throws SQLException {
		String note = String.format("Bought %.3f of %s @ $%.2f per share (+$20 Commission)", numOfShares, stockSym, stockPrice);
		return insertRecord(note, "buy");
	}

	//TODO: Ensure its added to all sell command
	public static boolean addSellRecord(String stockSym, double stockBoughtAt, double numOfShares) throws SQLException {
		double currentStockPrice = Stock.getStockPrice(stockSym);
		String note = String.format("Sold %.3f of %s @ $%.2f per share (bought @ $%.2f per share (+$20 Commission)", numOfShares, stockSym, currentStockPrice, stockBoughtAt);
		return insertRecord(note, "sell");
	}
	

	// Private Methods
	private static boolean insertRecord(String note, String type) throws SQLException {
		String query = String.format("INSERT INTO transactions (tax_id, date, month, txn_type, txn_details) VALUES (%d, '%s', %d, '%s', '%s');", User.currentTaxID, CommandUI.currentDate.toString(), CommandUI.currentDate.getMonth(), type, note);
		if ((JDBC.statement.executeUpdate(query)) == 1){
			return true;
		}
		return false;
	}

	
}
