import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class Manager extends User {

	/* COMMANDS */
	public static void addInterest(double interestRate) throws SQLException {
		MarketAccount.accrueInterestOnAllAccounts(interestRate);
		
		System.out.println("Successfully added interest to all market accounts.");
	}
	
	public static void generateMonthlyStatement(int taxID) throws SQLException {
		System.out.println("------------------ Monthly Report for Customer Start -------------------");
		String monthlyStatement = Transactions.generateMonthlyStatement(taxID);
		System.out.println(monthlyStatement);
		System.out.println("---------------- Monthly Report for Customer Completed -----------------");
	}
	
	public static void listActiveCustomers() throws SQLException {
		System.out.println("------------------------ Active Customer List -------------------------");
		System.out.println(String.format("%5s %-25s %-15s %s", "", "Name", "Username", "Tax ID"));
		String sub_query = String.format("(SELECT tax_id, SUM(ABS(num_shares)) AS traded FROM stock_accounts WHERE SUBSTRING_INDEX(date, '/', 1) = %s GROUP BY tax_id)", Integer.toString(CommandUI.currentDate.getMonth()));
		String query = String.format("SELECT name, username, C.tax_id FROM customer_profiles AS C, (SELECT tax_id FROM %s AS tab WHERE tab.traded >= 1000) AS Temp WHERE C.tax_id = Temp.tax_id ", sub_query);
		ResultSet result = JDBC.statement.executeQuery(query);

		if(!result.first()){
			System.out.println("No active customers for this month.");
			return;
		}

		do {
			System.out.println(String.format("%5s %-25s %-15s %d", "", result.getString("name"), result.getString("username"), result.getInt("tax_id")));
		} while(result.next());

		System.out.println("");
	}
	
	// DTER: Government Drug & Tax Evasion Report
	public static void generateDTER() throws SQLException {
		// TODO: TEST DEEPLY
		String query = "SELECT tax_id, SUM(CAST(ints.interest as DECIMAL(10,2))) AS interest  FROM (SELECT tax_id, SUBSTRING_INDEX(txn_details, ' ', -1) AS interest FROM transactions WHERE txn_type = 'interest') AS ints GROUP BY tax_id;";
		ResultSet result = JDBC.statement.executeQuery(query);
		HashMap<Integer, Double> interest_hm = new HashMap<Integer, Double>();

		while(result.next()){
			interest_hm.put(result.getInt("tax_id"), result.getDouble("interest"));
		}

		System.out.println("------------------------- DTER REPORT STARTED --------------------------");
		System.out.println(String.format("%5s %-8s %-25s %-5s %s", "", "Tax ID", "Name", "State", "Money Made"));

		query = "SELECT tax_id, name, state, SUM(earnings) AS earnings FROM stock_accounts NATURAL JOIN customer_profiles GROUP BY tax_id;";
		result = JDBC.statement.executeQuery(query);
		double gain;
		while(result.next()){
			if(interest_hm.containsKey(result.getInt("tax_id"))){
				gain = interest_hm.get(result.getInt("tax_id")) + result.getDouble("earnings");
			}
			else{
				gain = result.getDouble("earnings");
			}
			
			if(gain > 10000){
				System.out.println(String.format("%5s %-8d %-25s %-5s %.2f", "", result.getInt("tax_id"), result.getString("name"), result.getString("state"), gain));
			}
		}

		System.out.println("------------------------ DTER REPORT COMPLETED -------------------------");
	}
	
	public static void generateCustomerReport(int customerTaxID) throws SQLException {
		// TODO: Test
		System.out.println("\n------------------------ CUSTOMER REPORT INFORMATION -------------------------");
		String query = String.format("SELECT tax_id, name, email, balance FROM customer_profiles NATURAL JOIN market_accounts WHERE tax_id = %d;", customerTaxID);
		ResultSet result = JDBC.statement.executeQuery(query);
		if(result.first()){
			System.out.println(String.format("Customer Tax ID: %d", customerTaxID));
			System.out.println(String.format("Customer Contact: %s, %s", result.getString("name"), result.getString("email")));
			System.out.println(String.format("Market Account Balance: $%.2f", result.getDouble("balance")));
			System.out.println("Customer Stock Accounts:");
			System.out.println(String.format("%5s %-8s %-7s %-10s %s", "", "Tax ID", "STOCK", "Shares", "Earnings (till date)"));
			
			query = String.format("SELECT tax_id, stock_sym, SUM(num_shares) AS shares, SUM(earnings) AS earnings FROM stock_accounts WHERE tax_id = %d GROUP BY tax_id, stock_sym;", customerTaxID);
			result = JDBC.statement.executeQuery(query);
			while(result.next()){
				System.out.println(String.format("%5s %-8d %-7s %-10.3f %.2f", "", result.getInt("tax_id"), result.getString("stock_sym"), result.getDouble("shares"), result.getDouble("earnings")));
			}
		}
		else {
			System.out.println("No such customer found.");
		}
		

		System.out.println("------------------------ CUSTOMER REPORT COMPLETED -------------------------\n");
	}
	
	public static void deleteTransactions() throws SQLException {
		// TODO: Test
		String query = "DELETE FROM transactions";
		JDBC.statement.executeUpdate(query);
		System.out.println("Stored transactions deleted.\n");
	}

}
