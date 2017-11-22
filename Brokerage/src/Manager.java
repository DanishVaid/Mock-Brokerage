
public class Manager {

	private boolean inSession;
	
	public Manager() {
		this.inSession = true;
	}

	public boolean isInSession() {
		return inSession;
	}
	
	public void exitSession() {
		inSession = false;
	}

}
