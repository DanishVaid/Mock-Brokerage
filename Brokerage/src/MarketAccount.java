import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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
		System.out.println("New account balance is: $" + newBalance);
		return true;
	}
	
	public static boolean withdraw(double amount) throws SQLException {
		double originalBalance = getBalance();
		
		if (originalBalance > amount) {
			double newBalance = originalBalance - amount;
			
			String query = String.format("UPDATE market_accounts SET balance = %.2f WHERE tax_id = %d;", newBalance, User.currentTaxID);
			JDBC.statement.executeUpdate(query);
			
			newBalance = getBalance();
			System.out.println(String.format("New balance is: $%.2f", newBalance));
			return true;
		}
		else {
			System.out.println(String.format("Insufficient funds, account currently has: $%.2f", originalBalance));
			return false;
		}
	}
	
	public static void accrueInterestOnAllAccounts(double interestRate) throws SQLException {
		ResultSet sql_tax_ids = JDBC.statement.executeQuery("SELECT tax_id FROM market_accounts");
		ArrayList<Integer> tax_ids = new ArrayList<Integer>();
		while(sql_tax_ids.next()){
			tax_ids.add(sql_tax_ids.getInt("tax_id"));
		}

		String query = "";
		ResultSet sql_avg_daily_balance;
		double avg_daily_balance;
		double interest;
		for(int tax_id : tax_ids){
			// get AVG daily balance
			query = String.format("SELECT AVG(balance) FROM daily_balances WHERE tax_id = %d AND month = %d;", tax_id, CommandUI.currentDate.getMonth());
			sql_avg_daily_balance = (JDBC.statement.executeQuery(query));
			sql_avg_daily_balance.first();
			avg_daily_balance = sql_avg_daily_balance.getDouble(1);

			// Calculate interest and update balance
			interest = avg_daily_balance * interestRate;
			query = String.format("UPDATE market_accounts SET balance = balance + %.2f WHERE tax_id = %d;", interest, tax_id);
			JDBC.statement.executeUpdate(query);

			// Add to transactions table
			Transactions.addInterestRecord(tax_id, interest);
		}
	}
	
	public static boolean buy(double amount) throws SQLException {
		System.out.println("$20 Commission added to buy request, if successful.");
		return withdraw(amount + 20);

	}
	
	public static boolean sell(double amount) throws SQLException {
		System.out.println("$20 Commission added to sell request, if successful.");
		return deposit(amount - 20);
	}

	public static double getBalance() throws SQLException {
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d;", User.currentTaxID);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		return result.getDouble("balance");
	}

	public static double getBalance(int taxID) throws SQLException {
		String query = String.format("SELECT balance FROM market_accounts WHERE tax_id = %d;", taxID);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		return result.getDouble("balance");
	}

	public static void recordAllDailyBalances() throws SQLException {
		String query = "SELECT * FROM market_accounts";
		ResultSet result = JDBC.statement.executeQuery(query);

		String baseQuery = "INSERT INTO daily_balances (tax_id, balance, date, month, day) ";
		String valueQuery;
		ArrayList<String> recordingQueries = new ArrayList<String>();
		Date currentDate = CommandUI.currentDate;
		int success = 0;

		while(result.next()){
			valueQuery = String.format("VALUES (%d, %.2f, '%s', %d, %d);\n", result.getInt("tax_id"), result.getDouble("balance"), currentDate.toString(), currentDate.getMonth(), currentDate.getDay());
			recordingQueries.add(baseQuery + valueQuery);
		}

		for(String curr_record : recordingQueries) {
			success += JDBC.statement.executeUpdate(curr_record);
		}

		if(success > 0){
			System.out.println(String.format("Daily balances recorded for: %s\n", currentDate.toString()));
		}
		else {
			System.out.println(String.format("No market accounts found to record daily balance on: %s\n", currentDate.toString()));
		}
	}
}
