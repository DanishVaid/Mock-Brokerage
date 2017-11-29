import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
	
	/* LOG IN AND REGISTRATION */
	public static int register(String username, String password, char userTag) throws SQLException {
		if(userTag == 'm') {
			String query = String.format("SELECT * FROM managers WHERE username = '%s'", username);
			ResultSet result = JDBC.statement.executeQuery(query);
			while (result.next()) {
				System.out.println("Username already exists.");
				System.out.println("Enter your username and password to register, separated by a space.");
				String[] loginInfo = CommandUI.input.nextLine().split(" ");

				username = loginInfo[0];
				password = loginInfo[1];

				query = String.format("SELECT * FROM managers WHERE username = '%s'", username);
				result = JDBC.statement.executeQuery(query);
			}

			System.out.println("Name:");
			String name = CommandUI.input.nextLine();
			System.out.println("Address:");
			String address = CommandUI.input.nextLine();
			System.out.println("State:");
			String state = CommandUI.input.nextLine();
			System.out.println("Phone Number:");
			String phone_num = CommandUI.input.nextLine();
			System.out.println("Email:");
			String email = CommandUI.input.nextLine();

			// Verify Tax ID is unique
			System.out.println("Tax ID:");
			int tax_id = Integer.parseInt(CommandUI.input.nextLine());
			query = String.format("SELECT * FROM managers WHERE tax_id = '%d'", tax_id);
			result = JDBC.statement.executeQuery(query);
			while(result.next()){
				System.out.println("ERROR -- Tax ID already in system.");
				System.out.println("Please Enter Unique Tax ID");
				tax_id = Integer.parseInt(CommandUI.input.nextLine());

				query = String.format("SELECT * FROM managers WHERE tax_id = '%d'", tax_id);
				result = JDBC.statement.executeQuery(query);
			}

			// Verify SSN is unique
			System.out.println("SSN:");
			String ssn = CommandUI.input.nextLine();
			query = String.format("SELECT * FROM managers WHERE ssn = '%s'", ssn);
			result = JDBC.statement.executeQuery(query);
			while(result.next()){
				System.out.println("ERROR -- SSN already in system.");
				System.out.println("Please Enter Unique SSN");
				ssn = CommandUI.input.nextLine();

				query = String.format("SELECT * FROM managers WHERE ssn = '%s'", ssn);
				result = JDBC.statement.executeQuery(query);
			}

			System.out.println("User information collected, creating account");
			query = String.format("INSERT INTO managers VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%s');", 
									name, username, password, address, state, phone_num, email, tax_id, ssn);
			JDBC.statement.executeUpdate(query);

			System.out.println("Manager User Created");
		}
		else if(userTag == 't'){
			String query = String.format("SELECT * FROM customer_profiles WHERE username = '%s'", username);
			ResultSet result = JDBC.statement.executeQuery(query);
			while (result.next()) {
				System.out.println("Username already exists.");
				System.out.println("Enter your username and password to register, separated by a space.");
				String[] loginInfo = CommandUI.input.nextLine().split(" ");

				username = loginInfo[0];
				password = loginInfo[1];

				query = String.format("SELECT * FROM customer_profiles WHERE username = '%s'", username);
				result = JDBC.statement.executeQuery(query);
			}

			System.out.println("Name:");
			String name = CommandUI.input.nextLine();
			System.out.println("Address:");
			String address = CommandUI.input.nextLine();
			System.out.println("State:");
			String state = CommandUI.input.nextLine();
			System.out.println("Phone Number:");
			String phone_num = CommandUI.input.nextLine();
			System.out.println("Email:");
			String email = CommandUI.input.nextLine();

			// Verify Tax ID is unique
			System.out.println("Tax ID:");
			int tax_id = Integer.parseInt(CommandUI.input.nextLine());
			query = String.format("SELECT * FROM customer_profiles WHERE tax_id = '%d'", tax_id);
			result = JDBC.statement.executeQuery(query);
			while(result.next()){
				System.out.println("ERROR -- Tax ID already in system.");
				System.out.println("Please Enter Unique Tax ID");
				tax_id = Integer.parseInt(CommandUI.input.nextLine());

				query = String.format("SELECT * FROM customer_profiles WHERE tax_id = '%d'", tax_id);
				result = JDBC.statement.executeQuery(query);
			}

			// Verify SSN is unique
			System.out.println("SSN:");
			String ssn = CommandUI.input.nextLine();
			query = String.format("SELECT * FROM customer_profiles WHERE ssn = '%s'", ssn);
			result = JDBC.statement.executeQuery(query);
			while(result.next()){
				System.out.println("ERROR -- SSN already in system.");
				System.out.println("Please Enter Unique SSN");
				ssn = CommandUI.input.nextLine();

				query = String.format("SELECT * FROM customer_profiles WHERE ssn = '%s'", ssn);
				result = JDBC.statement.executeQuery(query);
			}

			System.out.println("User information collected, creating account");
			query = String.format("INSERT INTO customer_profiles VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s', '%d', '%s');", 
									name, username, password, address, state, phone_num, email, tax_id, ssn);
			JDBC.statement.executeUpdate(query);

			System.out.println("Trader User Created");
		}
		else{
			System.out.println("--- Should never have come here ---");
		}

		System.out.println("Enter Username and Password seperated by a space to continue logging in.");
		String[] loginInfo = CommandUI.input.nextLine().split(" ");
		username = loginInfo[0];
		password = loginInfo[1];
		
		return login(username, password, userTag);
	}
	
	public static int login(String username, String password, char userTag) throws SQLException {
		// Check if user exists and get taxID.
		if(userTag == 'm') {
			String query = String.format("SELECT * FROM managers WHERE username = '%s' AND password = '%s'", username, password);
			ResultSet result = JDBC.statement.executeQuery(query);
			while (!result.next()) {
				System.out.println("Username and password combination not recognized.");
				System.out.println("Enter your username and password to log in, separated by a space.");
				String[] loginInfo = CommandUI.input.nextLine().split(" ");

				username = loginInfo[0];
				password = loginInfo[1];

				query = String.format("SELECT * FROM managers WHERE username = '%s' AND password = '%s'", username, password);
				result = JDBC.statement.executeQuery(query);
			}

			return result.getInt("tax_id");
		}
		else if(userTag == 't'){
			String query = String.format("SELECT * FROM customer_profiles WHERE username = '%s' AND password = '%s'", username, password);
			ResultSet result = JDBC.statement.executeQuery(query);
			while (!result.next()) {
				System.out.println("Username and password combination not recognized.");
				System.out.println("Enter your username and password to log in, separated by a space.");
				String[] loginInfo = CommandUI.input.nextLine().split(" ");

				username = loginInfo[0];
				password = loginInfo[1];

				query = String.format("SELECT * FROM customer_profiles WHERE username = '%s' AND password = '%s'", username, password);
				result = JDBC.statement.executeQuery(query);
			}

			return result.getInt("tax_id");
		}
		else{
			System.out.println("--- Should never have come here ---");
		}
		
		return -1;
	}
}
