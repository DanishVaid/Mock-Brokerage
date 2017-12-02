import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketAccount {
	
	public static void deposit(int  tax_id, double amount) throws SQLException {
		String query = String.format("UPDATE market_accounts SET balance = balance + %.3f WHERE tax_id = %d;", amount, tax_id);
		JDBC.statement.executeUpdate(query);
		double new_bal = getBalance(tax_id);
		System.out.println("Money (" + amount + ") deposited successfully");
		System.out.println("New account balance is: " + new_bal);
	}
	
	public static void withdraw(int tax_id, double amount) throws SQLException {
		double curr_bal = getBalance(tax_id);
		if(curr_bal > amount){
			double new_bal = curr_bal - amount;
			query = String.format("UPATE market_account SET balance = %.3f WHERE tax_id = %d", new_bal, tax_id);
			JDBC.statement.executeUpdate(query);
			new_bal = getBalance(tax_id);
			System.out.println(String.format("Money (%.3f) withdrawn successfully", amount));
			System.out.println(String.format("New balance is: %.3f", new_bal));
		}
		else {
			System.out.println(String.format("Insufficient funds, account currently has: %.3f", curr_bal));
		}
	}
	
	public static void accrueInterest(double percent) {
		// TODO: SQL query, update customer balance
		// Initial thought: manager calls this function when they run add interest
		// Another possibility: delete this function and the manager updates the SQL tables
		// Thought: maybe the two functions are not the same thing.
		
	}
	
	public static void buy(int tax_id, double amount) throws SQLException {
		// Simply takes out the approprate amount from user's account
		withdraw(tax_id, amount);
		
	}
	
	public static void sell(int tax_id, double amount) throws SQLException {
		// Simply gives the user the approprate amount from sale
		deposit(tax_id, amount);
	}

	public static double getBalance(int tax_id) throws SQLException {
		// SQL query for balance attribute in table for this tax_id
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d", tax_id);
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		return result.getDouble("balance");
	}
}
