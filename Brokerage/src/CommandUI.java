import java.sql.SQLException;
import java.util.Scanner;

public class CommandUI {

	public static void main(String[] args) {		
		welcome();
		String userType = getUserType();
		String[] loginInfo = getLoginInfo();
		
		// Manager commands and actions.
		if (userType.equals("manager")) {
			// TODO: Ask question - Is the manager an admin, who can open/close the day, roll forward number of days?
			String username = attemptSignIn(loginInfo, 'm');

			System.out.println("Logged in as: " + username);
			takeCommandAsManager();
		}
		// Trader commands and actions.
		else if (userType.equals("trader")) {	
			String username = attemptSignIn(loginInfo, 't');
	
			System.out.println("Logged in as: " + username);
			takeCommandAsTrader();
		}
		// Admin commands and actions.
		else if (userType.equals("admin")) {
			System.out.println("Logged in as: admin");
			takeCommandAsAdmin();
		}
		else {
			System.out.println("ERROR: Should never enter here.");
		}
	}
	
	private static void welcome() {
		// Welcome message to user.
		System.out.println("Hello, welcome to program.");
		System.out.println("Are you a 'manager' or 'trader':");
	}
	
	private static String getUserType() {
		// TODO: Add admin user.
		// Get user type between manager, trader, and admin.
		Scanner input = new Scanner(System.in);
		String userType = input.nextLine().toLowerCase();
		
		while ((!userType.equals("manager")) || (!userType.equals("trader"))) {
			System.out.println("Cannot understand your input, please try again:");
			userType = input.nextLine().toLowerCase();
		}
		
		input.close();
		return userType;
	}
	
	private static String[] getLoginInfo() {
		// Login or register as a user.
		System.out.println("Enter your username and password to log in, separated by a space.");
		System.out.println("If you want to register an account, append an r to your login info, separated by a space.");
		
		Scanner input = new Scanner(System.in);
		String[] loginInfo = input.nextLine().split(" ");
		
		while (!(loginInfo.length == 2) || !(loginInfo.length == 3)) {
			System.out.println("Incorrect number of arguments, please read the above instructions.");
			loginInfo = input.nextLine().split(" ");
		}
		
		input.close();
		return loginInfo;
	}
	
	private static String attemptSignIn(String[] loginInfo, char userType) {
		Scanner input = new Scanner(System.in);
		
		// Will not leave loop until valid credentials
		if (loginInfo.length == 3) {
			while (!User.register(loginInfo[0], loginInfo[1], userType)) {
				System.out.println("This user already exists, please use another username.");
				loginInfo = input.nextLine().split(" ");
			}
		}
		else if (loginInfo.length == 2) {
			while (!User.login(loginInfo[0], loginInfo[1], userType)) {
				System.out.println("Invalid username or password. Please try again.");
				loginInfo = input.nextLine().split(" ");
			}
		}
		else {
			System.out.println("ERROR: Should never enter here.");
		}
		
		input.close();
		return loginInfo[0];
	}
	
	private static void takeCommandAsManager() {
		Scanner input = new Scanner(System.in);
		
		try {
			JDBC.setup();
			
			while (true) {
				System.out.println("What would you like to do?");
				System.out.println("---COMMANDS---");
				
				String[] action = input.nextLine().toLowerCase().split(" ");
				// TODO: Add commands.
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			input.close();
			try {
				JDBC.endSession();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void takeCommandAsTrader() {
		
	}
	
	private static void takeCommandAsAdmin() {
		
	}

}
