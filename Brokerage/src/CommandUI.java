import java.util.Scanner;

public class CommandUI {

	public static void main(String[] args) {
		try {
			JDBC.setup();
		} catch (Exception e) {
			//TODO: handle exception
			System.out.println(e);
		}
		/* 
		// Welcome message, select user type.
		System.out.println("Hello, welcome to program.");
		System.out.println("Are you a 'manager' or 'trader':");
		
		// TODO: Possibly add debugger user
		
		Scanner input = new Scanner(System.in);
		String userType = input.nextLine().toLowerCase();
		
		while ((!userType.equals("manager")) || (!userType.equals("trader"))) {
			System.out.println("Cannot understand your input, please try again:");
			userType = input.nextLine().toLowerCase();
		}
		
		// Login or register as a user.
		System.out.println("Enter your username and password to log in, separated by a space.");
		System.out.println("If you want to register an account, append an r to your login info, separated by a space.");
		
		String[] loginInfo = input.nextLine().split(" ");
		
		while (!(loginInfo.length == 2) || !(loginInfo.length == 3)) {
			System.out.println("Incorrect number of arguments, please read the above instructions.");
			loginInfo = input.nextLine().split(" ");
		}
		
		// Manager commands and actions.
		if (userType.equals("manager")) {			
			if (loginInfo.length == 3) {
				while (!User.register(loginInfo[0], loginInfo[1], 'm')) {
					System.out.println("This user already exists, please use another username.");
					loginInfo = input.nextLine().split(" ");
				}
			}
			else if (loginInfo.length == 2) {
				while (!User.login(loginInfo[0], loginInfo[1], 'm')) {
					System.out.println("Invalid username or password. Please try again.");
					loginInfo = input.nextLine().split(" ");
				}
			}
			else {
				System.out.println("ERROR: Should never enter here.");
			}
			
			Manager manager = new Manager(loginInfo[0], loginInfo[1]);
			System.out.println("Logged in as: " + manager.getUsername());
			while (manager.isInSession()) {
				System.out.println("What would you like to do?");
				System.out.println("---COMMANDS---");
				
				String[] action = input.nextLine().toLowerCase().split(" ");
				// TODO: Add commands.
			}
		}
		// Trader commands and actions.
		else if (userType.equals("trader")) {	
			if (loginInfo.length == 3) {
				while (!User.register(loginInfo[0], loginInfo[1], 't')) {
					System.out.println("This user already exists, please use another username.");
					loginInfo = input.nextLine().split(" ");
				}
			}
			else if (loginInfo.length == 2) {
				while (!User.login(loginInfo[0], loginInfo[1], 't')) {
					System.out.println("Invalid username or password. Please try again.");
					loginInfo = input.nextLine().split(" ");
				}
			}
			else {
				System.out.println("ERROR: Should never enter here.");
			}
			
			Trader trader = new Trader(loginInfo[0], loginInfo[1]);
			System.out.println("Logged in as: " + trader.getUsername());
			while (trader.isInSession()) {
				System.out.println("What would you like to do?");
				System.out.println("---COMMANDS---");
				
				String[] action = input.nextLine().toLowerCase().split(" ");
				// TODO: Add commands.
			}
		}
		else {
			System.out.println("ERROR: Should never enter here.");
		}
		
		// End session.
		input.close();

		*/
	}
}
