
public class Trader {

	private boolean inSession;
	
	public Trader() {
		this.inSession = true;
	}
	
	
	/* COMMANDS */
	
	
	
	/* LOG IN AND REGISTRATION */
	
	
	
	/* GETTER AND SETTERS */

	public boolean isInSession() {
		return inSession;
	}
	
	public void exitSession() {
		inSession = false;
	}

}
