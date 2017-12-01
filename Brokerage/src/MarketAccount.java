import java.sql.ResultSet;
import java.sql.SQLException;

public class MarketAccount {
	
	public static void deposit(int  tax_id, double amount) throws SQLException {
		String query = String.format("UPDATE market_accounts SET balance = balance + %.4f WHERE tax_id = %d;", amount, tax_id);
		JDBC.statement.executeUpdate(query);
		String get_new_amount = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d", tax_id);
		ResultSet result = JDBC.statement.executeQuery(get_new_amount);
		result.first();
		System.out.println("Money (" + amount + ") deposited successfully");
		System.out.println("New account balance is: " + result.getDouble("balance"));
	}
	
	public static void withdraw(int tax_id, double amount) throws SQLException {
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d", tax_id);
	}
	
	public static void accrueInterest(double percent) {
		// TODO: SQL query, update customer balance
		// Initial thought: manager calls this function when they run add interest
		// Another possibility: delete this function and the manager updates the SQL tables
		// Thought: maybe the two functions are not the same thing.
		
	}
	
	public static void buy(/* Idk what parameters */) {
		// TODO: Figure out how to buy
		
	}
	
	public static void sell(/* Idk what parameters */) {
		// TODO: Figure out how to sell
	}

	public static void getBalance() {
		// TODO: SQL query for balance attribute in table
		
	}
}
