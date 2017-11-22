
public class User {

	private String username;
	private String password;
	private boolean inSession;
	
	public User() {
		inSession = false;
	}
	
	/* LOG IN AND REGISTRATION */
	public boolean register(String username, String password) {
		// TODO: Check if user does not exist
		
		
		// TODO: Add user to system
		
		
		// Log in to user
		login(username, password);	// User does not have to log in after registering
		
		return false;
	}
	
	public boolean login(String username, String password) {
		// Check if user exists
		
		return false;
	}
	
	/* GETTER AND SETTERS */
	public boolean isInSession() {
		return inSession;
	}
	
	public void exitSession() {
		inSession = false;
	}
}
