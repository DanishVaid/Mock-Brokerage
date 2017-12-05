import java.sql.SQLException;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

public class Operator {

	public static boolean canTrade;
	
	public static void resetDB() throws SQLException {
		deleteDB();
		generateTables();
		importInitialData();
		System.out.println("Success resetting system.");
	}

	public static void setSystem() throws SQLException, Exception {
		String query = "SELECT * FROM system_status;";
		ResultSet result = JDBC.statement.executeQuery(query);
		if(result.first()){
			canTrade = result.getBoolean("market_open");
			CommandUI.currentDate = Date.buildFromString(result.getString("date"));
			System.out.println("System Initialized");
		}
		else {
			throw new Exception("No system status found.");
		}
	}
	
	public static void openMarket() throws SQLException {
		if(!canTrade){
			canTrade = true;
			recordOpenMarket();
			System.out.println("Market Opened.\n");
		}
		else {
			System.out.println("Market Already Open.\n");
		}
	}
	
	// Signifies the end of a day.
	public static void closeMarket() throws SQLException {
		canTrade = false;
		recordCloseMarket();
		MarketAccount.recordAllDailyBalances();
		CommandUI.currentDate.addDays(1);
		recordCurrentDate(CommandUI.currentDate);
		System.out.println("Market Closed.\n");
	}
	
	public static void setStockPrice(String stockSymbol, double newPrice) throws SQLException {
		Stock.setStockPrice(stockSymbol, newPrice);
	}
	
	public static void setDate(int month, int day, int year) throws SQLException {
		Date desiredDate = new Date(month, day, year);
		int differenceOfDays = CommandUI.currentDate.getDifference(desiredDate);
		
		boolean tempCanTrade = canTrade;
		for (int i = 0; i < differenceOfDays; i++) {
			closeMarket();
			openMarket();
		}
		
		if (tempCanTrade) {
			openMarket();
		}
		else {
			closeMarket();
		}
		
	}

	/*** PRIVATE METHODS ***/

	private static void generateTables() throws SQLException {
		String[] tables = new String[9];
		tables[0] = "CREATE TABLE customer_profiles ( "
				+ "name CHAR(30) NOT NULL, "
				+ "username CHAR(30) NOT NULL, "
				+ "password CHAR(30) NOT NULL, "
				+ "address CHAR(40) NOT NULL, "
				+ "state CHAR(2) NOT NULL, "
				+ "phone CHAR(15) NOT NULL, "
				+ "email CHAR(30) NOT NULL, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "ssn CHAR(11) NOT NULL, "
				+ "PRIMARY KEY(tax_id) "
				+ ");";
		tables[1] = "CREATE TABLE market_accounts ( "
				+ "tax_id INT UNSIGNED NOT NULL,"
				+ "balance DECIMAL(10, 2) NOT NULL, "
				+ "PRIMARY KEY(tax_id), "
				+ "FOREIGN KEY(tax_id) REFERENCES customer_profiles(tax_id) "
				+ ");";
		tables[2] = "CREATE TABLE actor_stocks ( "
				+ "stock_sym CHAR(3) NOT NULL, "
				+ "current_price DECIMAL(10, 2) NOT NULL, "
				+ "actor_name CHAR(30) NOT NULL, "
				+ "dob CHAR(20) NOT NULL, "
				+ "PRIMARY KEY(stock_sym) "
				+ ")";
		tables[3] = "CREATE TABLE transactions ( "
				+ "txn_id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "date CHAR(30) NOT NULL, "
				+ "month INT UNSIGNED NOT NULL, "
				+ "txn_type CHAR(30) NOT NULL, "
				+ "txn_details CHAR(200) NOT NULL, "
				+ "PRIMARY KEY(txn_id), "
				+ "FOREIGN KEY(tax_id) REFERENCES customer_profiles(tax_id), "
				+ "CHECK(txn_type='deposit' OR txn_type='withdraw' OR txn_type='buy' OR txn_type='sell' OR txn_type='interest') "
				+ ");";
		tables[4] = "CREATE TABLE stock_accounts ( "
				+ "stock_acc_id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "stock_sym CHAR(3) NOT NULL, "
				+ "num_shares DECIMAL(10, 3) NOT NULL,"
				+ "date CHAR(20) NOT NULL, "
				+ "type CHAR(10) NOT NULL, "
				+ "price DECIMAL(10, 2) NOT NULL,"
				+ "earnings DECIMAL(10, 2) NOT NULL,"
				+ "PRIMARY KEY(stock_acc_id), "
				+ "FOREIGN KEY(tax_id) REFERENCES customer_profiles(tax_id), "
				+ "FOREIGN KEY(stock_sym) REFERENCES actor_stocks(stock_sym), "	// May not need this foreign key.
				+ "CHECK(type='buy' OR type='sell') "
				+ ");";
		tables[5] = "CREATE TABLE contracts ( "
				+ "c_id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
				+ "stock_sym CHAR(3) NOT NULL, "
				+ "movie_title CHAR(30) NOT NULL, "
				+ "role CHAR(30) NOT NULL, "
				+ "year INT NOT NULL, "
				+ "value DECIMAL(10, 2) NOT NULL, "
				+ "PRIMARY KEY(c_id), "
				+ "FOREIGN KEY(stock_sym) REFERENCES actor_stocks(stock_sym), "
				+ "CHECK(role='actor' OR role='director' OR role='both') "
				+ ");";

		tables[6] = "CREATE TABLE system_status ( "
				+ "market_open BIT NOT NULL, "
				+ "date CHAR(30) NOT NULL "
				+ ");";
		
		tables[7] = "CREATE TABLE managers ("
				+ "name CHAR(30) NOT NULL, "
				+ "username CHAR(30) NOT NULL, "
				+ "password CHAR(30) NOT NULL, "
				+ "address CHAR(40) NOT NULL, "
				+ "state CHAR(2) NOT NULL, "
				+ "phone CHAR(15) NOT NULL, "
				+ "email CHAR(30) NOT NULL, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "ssn CHAR(11) NOT NULL, "
				+ "PRIMARY KEY(tax_id) "
				+ ");";
		tables[8] = "CREATE TABLE daily_balances ("
				+ "b_id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "balance DECIMAL(10, 2) NOT NULL, "
				+ "date CHAR(20) NOT NULL, "
				+ "month INT UNSIGNED NOT NULL, "
				+ "day INT UNSIGNED NOT NULL, "
				+ "PRIMARY KEY(b_id), "
				+ "FOREIGN KEY(tax_id) REFERENCES market_accounts(tax_id)"
				+ ");";
		
		// Insert one and only one row into system_status.
		String initSystemStatus = "INSERT INTO system_status "
				+ "VALUES (0, '3/16/2013') "
				+ ";";
		
		for (int i = 0 ; i < tables.length; i++) {
			JDBC.statement.executeUpdate(tables[i]);
		}
		
		System.out.println("--- All tables created ---");
		JDBC.statement.executeUpdate(initSystemStatus);
		System.out.println("--- System Status Initialized ---");
	}

	private static void deleteDB() throws SQLException {
		String[] tableNames = {
			"daily_balances",
			"market_accounts",
			"transactions",
			"stock_accounts",
			"contracts",
			"actor_stocks",
			"system_status",
			"customer_profiles",
			"managers"
		};

		for(String tableName : tableNames){
			JDBC.statement.executeUpdate("DROP TABLE " + tableName + ";");
		}

		System.out.println("--- Deleted all table ---");
	}

	private static void importInitialData() throws SQLException {
		File directory = new File(System.getProperty("user.dir"));
	    directory = directory.getParentFile();
		directory = directory.getParentFile();
		
		String[] tableNames = {
			"customer_profiles",
			"market_accounts",
			"actor_stocks",
			"stock_accounts",
			"contracts",
			"managers"
		};

		for(String tableName : tableNames){
			String filePath = String.format("/sampleData/%s.data", tableName);
			File dataFile = new File(directory, filePath);
			populateDataFromFile(dataFile, tableName);
		}

		System.out.println("--- All Data Imported ---");
	}

	private static void populateDataFromFile(File dataFile, String tableName) throws SQLException {

		BufferedReader dataBufferedReader = null;
		try {
			dataBufferedReader = new BufferedReader(new FileReader(dataFile));

			// Reads file into array of strings.
			List<String> list = new ArrayList<String>();
			String line = "";
			while((line = dataBufferedReader.readLine()) != null){
				list.add(line);
			}
			String[] lines = list.toArray(new String[0]);
			
			// Build query prefix for inserting values.
			String baseString = "INSERT INTO " + tableName + " (";
			String[] attributes = lines[0].split(",");
	
			baseString += attributes[0];
			for(int i = 1; i < attributes.length; i++) {
				baseString += ", " + attributes[i];
			}
			baseString += ") VALUES (";
	
			// For each row, build query to insert into DB.
			for(int i = 1; i < lines.length; i++) { 
				String[] currentRow = lines[i].split(",");
				String values = currentRow[0];
				for(int j = 1; j < currentRow.length; j ++) {
					values += ", " + currentRow[j];
				}
				String query = baseString + values + ");";
				
				JDBC.statement.executeUpdate(query);
			}
		}
		catch (IOException e) {
			System.out.println("IOException thrown.");
			e.printStackTrace();
		}
		finally {
			try {
				dataBufferedReader.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void recordOpenMarket() throws SQLException{
		String query = "UPDATE system_status SET market_open = 1;";
		JDBC.statement.executeUpdate(query);
	}

	public static void recordCloseMarket() throws SQLException {
		String query = "UPDATE system_status SET market_open = 0;";
		JDBC.statement.executeUpdate(query);
	}

	//TODO: Add to date change
	public static void recordCurrentDate(Date currDate) throws SQLException {
		String query = String.format("UPDATE system_status SET date = '%s';", currDate.toString());
		JDBC.statement.executeUpdate(query);
	}
	
}
