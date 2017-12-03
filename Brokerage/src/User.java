import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	public static int currentTaxID;

	public static boolean register(String username, String password, char userTag) throws SQLException {
		String tableName = "";
		if (userTag == 'm') {
			tableName = "managers";
		}
		else if (userTag == 't') {
			tableName = "customer_profiles";
		}
		else {
			System.out.println("--- Should never have come here ---");
			return false;
		}

		// Check for available username.
		String[] updatedLoginInfo = checkUserExists(username, password, tableName);
		username = updatedLoginInfo[0];
		password = updatedLoginInfo[1];
		
		storeUserInfo(username, password, tableName);

		System.out.println("User Created.");

		System.out.println("Enter Username and Password seperated by a space to continue logging in.");
		String[] loginInfo = CommandUI.input.nextLine().split(" ");
		username = loginInfo[0];
		password = loginInfo[1];
		
		return login(username, password, userTag);
	}
	
	public static boolean login(String username, String password, char userTag) throws SQLException {
		String tableName = "";
		if (userTag == 'm') {
			tableName = "managers";
		}
		else if (userTag == 't') {
			tableName = "customer_profiles";
		}
		else {
			System.out.println("--- Should never have come here ---");
			return false;
		}
		
		// Check if user exists and get taxID.
		ResultSet result = getUserFromTable(username, password, tableName);
		currentTaxID = result.getInt("tax_id");
		
		return true;
	}
	
	/*** PRIVATE METHODS ***/
	
	private static String[] checkUserExists(String username, String password, String tableType) throws SQLException {
		String query = String.format("SELECT * FROM %s WHERE username = '%s'", tableType, username);
		ResultSet result = JDBC.statement.executeQuery(query);
		while (result.next()) {
			System.out.println("Username already exists.");
			System.out.println("Enter your username and password to register, separated by a space.");
			String[] loginInfo = CommandUI.input.nextLine().split(" ");

			username = loginInfo[0];
			password = loginInfo[1];

			query = String.format("SELECT * FROM %s WHERE username = '%s'", tableType, username);
			result = JDBC.statement.executeQuery(query);
		}
		
		String[] loginInfo = {username, password};
		return loginInfo;
	}
	
	private static void storeUserInfo(String username, String password, String tableType) throws SQLException {
		System.out.println("Name:");
		String name = CommandUI.input.nextLine();
		System.out.println("Address:");
		String address = CommandUI.input.nextLine();
		System.out.println("State:");
		String state = CommandUI.input.nextLine();
		System.out.println("Phone Number:");
		String phoneNumber = CommandUI.input.nextLine();
		System.out.println("Email:");
		String email = CommandUI.input.nextLine();

		String query = "";
		ResultSet result = null;
		
		// Verify Tax ID is unique
		System.out.println("Tax ID:");
		int taxID = Integer.parseInt(CommandUI.input.nextLine());
		query = String.format("SELECT * FROM %s WHERE tax_id = '%d'", tableType, taxID);
		result = JDBC.statement.executeQuery(query);
		while(result.next()){
			System.out.println("ERROR -- Tax ID already in system.");
			System.out.println("Please Enter Unique Tax ID:");
			taxID = Integer.parseInt(CommandUI.input.nextLine());

			query = String.format("SELECT * FROM %s WHERE tax_id = '%d'", tableType, taxID);
			result = JDBC.statement.executeQuery(query);
		}
		
		query = "";
		result = null;

		// Verify SSN is unique
		System.out.println("SSN:");
		String ssn = CommandUI.input.nextLine();
		query = String.format("SELECT * FROM %s WHERE ssn = '%s'", tableType, ssn);
		result = JDBC.statement.executeQuery(query);
		while(result.next()){
			System.out.println("ERROR -- SSN already in system.");
			System.out.println("Please Enter Unique SSN");
			ssn = CommandUI.input.nextLine();

			query = String.format("SELECT * FROM %s WHERE ssn = '%s'", tableType, ssn);
			result = JDBC.statement.executeQuery(query);
		}
		
		query = "";

		System.out.println("User information collected, creating account...");
		query = String.format("INSERT INTO %s VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%s');", 
								tableType, name, username, password, address, state, phoneNumber, email, taxID, ssn);
		JDBC.statement.executeUpdate(query);
		
		MarketAccount.createAccount();
	}
	
	private static ResultSet getUserFromTable(String username, String password, String tableType) throws SQLException {
		String query = String.format("SELECT * FROM %s WHERE username = '%s' AND password = '%s'", tableType, username, password);
		ResultSet result = JDBC.statement.executeQuery(query);
		while (!result.next()) {
			System.out.println("Username and password combination not recognized.");
			System.out.println("Enter your username and password to log in, separated by a space.");
			String[] loginInfo = CommandUI.input.nextLine().split(" ");

			username = loginInfo[0];
			password = loginInfo[1];

			query = String.format("SELECT * FROM %s WHERE username = '%s' AND password = '%s'", tableType, username, password);
			result = JDBC.statement.executeQuery(query);
		}
		
		return result;
	}
}
