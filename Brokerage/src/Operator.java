import java.sql.SQLException;

public class Operator {

	public static void initializeDB() throws SQLException {
		String[] tables = new String[7];
		tables[0] = "CREATE TABLE customer_profiles ( "
				+ "name CHAR(30) NOT NULL, "
				+ "username CHAR(30) NOT NULL, "
				+ "password CHAR(30) NOT NULL, "
				+ "address CHAR(40) NOT NULL, "
				+ "state CHAR(2) NOT NULL, "
				+ "phone CHAR(10) NOT NULL, "
				+ "email CHAR(30) NOT NULL, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "ssn CHAR(11) NOT NULL, "
				+ "PRIMARY KEY(tax_id) "
				+ ");";
		tables[1] = "CREATE TABLE market_accounts ( "
				+ "tax_id INT UNSIGNED NOT NULL,"
				+ "balance DECIMAL NOT NULL, "
				+ "PRIMARY KEY(tax_id), "
				+ "FOREIGN KEY(tax_id) REFERENCES customer_profiles(tax_id) "
				+ ");";
		tables[2] = "CREATE TABLE actor_stocks ( "
				+ "stock_sym CHAR(3) NOT NULL, "
				+ "current_price DECIMAL NOT NULL, "
				+ "actor_name CHAR(30) NOT NULL, "
				+ "dob CHAR(20) NOT NULL, "
				+ "PRIMARY KEY(stock_sym) "
				+ ")";
		tables[3] = "CREATE TABLE transactions ( "
				+ "txn_id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "date CHAR(30) NOT NULL, "
				+ "txn_type CHAR(30) NOT NULL, "
				+ "PRIMARY KEY(txn_id), "
				+ "FOREIGN KEY(tax_id) REFERENCES customer_profiles(tax_id), "
				+ "CHECK(txn_type='deposit' OR txn_type='withdraw' OR txn_type='buy' OR txn_type='sell' OR txn_type='interest') "
				+ ");";
		tables[4] = "CREATE TABLE stock_accounts ( "
				+ "stock_acc_id INT UNSIGNED NOT NULL AUTO_INCREMENT, "
				+ "tax_id INT UNSIGNED NOT NULL, "
				+ "stock_sym CHAR(3) NOT NULL, "
				+ "date CHAR(20) NOT NULL, "
				+ "type CHAR(10) NOT NULL, "
				+ "price DECIMAL NOT NULL,"
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
				+ "value DOUBLE NOT NULL, "
				+ "PRIMARY KEY(c_id), "
				+ "FOREIGN KEY(stock_sym) REFERENCES actor_stocks(stock_sym), "
				+ "CHECK(role='actor' OR role='director' OR role='both') "
				+ ");";

		tables[6] = "CREATE TABLE system_status ( "
				+ "market_open BIT NOT NULL, "
				+ "date CHAR(30) NOT NULL "
				+ ");";
		
		// Insert one and only row into system_status.
		String initSystemStatus = "INSERT INTO system_status "
				+ "VALUES (0, '3/16/2013') "
				+ ";";
		
		for (int i = 0 ; i < tables.length; i++) {
			JDBC.statement.executeUpdate(tables[i]);
			System.out.println("Table '" + tables[i].split("\\s+")[2] + "' Created");
		}
		System.out.println("--- All tables created ---");
		JDBC.statement.executeUpdate(initSystemStatus);
		
		System.out.println("Success intializing system status.");
	}
	
	public static void resetDB() throws SQLException {
		deleteDB();
		initializeDB();
	}

	public static void deleteDB() throws SQLException {
		String[] tables = {
			"market_accounts",
			"transactions",
			"stock_accounts",
			"contracts",
			"actor_stocks",
			"system_status",
			"customer_profiles"
		};

		for(String table : tables){
			JDBC.statement.executeUpdate("DROP TABLE " + table + ";");
			System.out.println("Dropped '" + table + "' table");
		}

		System.out.println("--- Deleted all table ---");
	}
	
	public static void openMarket() {
		
	}
	
	public static void closeMarket() {
		
	}
	
	public static void setStockPrice() {
		
	}
	
	public static void setDate() {
		
	}
	
}
