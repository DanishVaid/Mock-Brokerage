import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketAccount {
	
	public static void deposit(int taxID, double amount) throws SQLException {
		String query = String.format("UPDATE market_accounts SET balance = balance + %.2f WHERE tax_id = %d;", amount, taxID);
		JDBC.statement.executeUpdate(query);
		
		double newBalance = getBalance(taxID);
		System.out.println("New account balance is: " + newBalance);
	}
	
	public static void withdraw(int taxID, double amount) throws SQLException {
		double originalBalance = getBalance(taxID);
		
		if (originalBalance > amount) {
			double newBalance = originalBalance - amount;
			
			String query = String.format("UPATE market_account SET balance = %.2f WHERE tax_id = %d", newBalance, taxID);
			JDBC.statement.executeUpdate(query);
			
			newBalance = getBalance(taxID);
			System.out.println(String.format("New balance is: %.2f", newBalance));
		}
		else {
			System.out.println(String.format("Insufficient funds, account currently has: %.2f", originalBalance));
		}
	}
	
	// TODO: Do.
	public static void accrueInterestOnAllAccounts(double interestRate) {
		// Initial thought: manager calls this function when they run add interest
	
	}
	
	public static void buy(int taxID, double amount) throws SQLException {
		withdraw(taxID, amount);
	}
	
	public static void sell(int taxID, double amount) throws SQLException {
		deposit(taxID, amount);
	}

	public static double getBalance(int taxID) throws SQLException {
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d", taxID);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		return result.getDouble("balance");
	}
}
