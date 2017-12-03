import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketAccount {

	public static void createAccount(int tax_id) throws SQLException {
		String query = String.format("INSERT INTO market_accounts (tax_id, balance) VALUES (%d, %d);", tax_id, 1000);
		JDBC.statement.executeUpdate(query);
		System.out.println(String.format("Successfully created account for tax ID: %d, initial balance 1000", getBalance()));
	}
	
	public static boolean deposit(double amount) throws SQLException {
		String query = String.format("UPDATE market_accounts SET balance = balance + %.2f WHERE tax_id = %d;", amount, User.currentTaxID);
		JDBC.statement.executeUpdate(query);
		
		double newBalance = getBalance();
		System.out.println("New account balance is: " + newBalance);
		return true;
	}
	
	public static boolean withdraw(double amount) throws SQLException {
		double originalBalance = getBalance();
		
		if (originalBalance > amount) {
			double newBalance = originalBalance - amount;
			
			String query = String.format("UPATE market_account SET balance = %.2f WHERE tax_id = %d", newBalance, User.currentTaxID);
			JDBC.statement.executeUpdate(query);
			
			newBalance = getBalance();
			System.out.println(String.format("New balance is: %.2f", newBalance));
			return true;
		}
		else {
			System.out.println(String.format("Insufficient funds, account currently has: %.2f", originalBalance));
			return false;
		}
	}
	
	// TODO: Do.
	public static void accrueInterestOnAllAccounts(double interestRate) {
		// Initial thought: manager calls this function when they run add interest
	
	}
	
	public static boolean buy(double amount) throws SQLException {
		return withdraw(amount);
	}
	
	public static boolean sell(double amount) throws SQLException {
		return deposit(amount);
	}

	public static double getBalance() throws SQLException {
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d", User.currentTaxID);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		return result.getDouble("balance");
	}

	public static double getBalance(int taxID) throws SQLException {
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d", taxID);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		return result.getDouble("balance");
	}
}
