
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
	public static boolean register(String username, String password, char userType) {
		// TODO: Check if user does not exist
		
		
		// TODO: Add user to system
		
		
		// Log in to user
		login(username, password, userType);	// User does not have to log in after registering
		
		return false;
	}
	
	public static boolean login(String username, String password, char userType) {
		// TODO: Check if user exists
		
		
		// TODO: Grant access
		
		
		return false;
	}
	
	/* GETTER AND SETTERS */
	public boolean isInSession() { return inSession; }
	public void exitSession() { inSession = false; }

	public String getUsername() { return username; }
}
