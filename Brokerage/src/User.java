import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

	protected String username;
	protected String password;
	protected boolean inSession;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.inSession = true;
	}
	
	/* LOG IN AND REGISTRATION */
	public static int register(String username, String password, char userTag) throws SQLException {
		// TODO: Check if user does not exist
		
		
		// TODO: Add user to system
		
		
		// Log in to user
		login(username, password, userTag);	// User does not have to log in after registering
		
		return -1;
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
	
	/* GETTER AND SETTERS */
	public boolean isInSession() { return inSession; }
	public void exitSession() { inSession = false; }

	public String getUsername() { return username; }
}
