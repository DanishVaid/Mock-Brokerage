import java.sql.SQLException;
import java.util.Arrays;
import java.util.Scanner;

public class CommandUI {

	public static Date currentDate;
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {		
		String userType = getUserType();
		String[] loginInfo = (!userType.equals("operator")) ? getLoginInfo() : null;
		
		try{
			JDBC.setup();
			Operator.resetDB();
			Operator.setMarketStatus();
			int x = 1 / 0;

			// Manager commands and actions.
			if (userType.equals("manager")) {
				int taxID = signIn(loginInfo, 'm');

				System.out.println("Logged in successful.");
				takeCommandAsManager();
			}
			// Trader commands and actions.
			else if (userType.equals("trader")) {	
				int taxID = signIn(loginInfo, 't');
		
				System.out.println("Logged in successful.");
				takeCommandAsTrader();
			}
			// Operator commands and actions.
			else if (userType.equals("operator")) {
				System.out.println("Logged in as operator.");
				takeCommandAsOperator();
			}
			else {
				System.out.println("ERROR: Should never enter here.");
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
	
	private static String getUserType() {
		// Welcome message to user.
		System.out.println("Hello, welcome to program.");
		System.out.println("Are you a 'manager', 'trader', or 'operator':");

		// Get user type between manager, trader, and operator.
		String userType = input.nextLine().toLowerCase();
		
		while ((!userType.equals("manager")) && (!userType.equals("trader")) && (!userType.equals("operator"))) {
			System.out.println("Cannot understand your input, please try again:");
			userType = input.nextLine().toLowerCase();
		}
		
		return userType;
	}
	
	private static String[] getLoginInfo() {
		// Login or register as a user.
		System.out.println("Enter your username and password to log in, separated by a space.");
		System.out.println("If you want to register an account, append an r to your login info, separated by a space.");
		
		String[] loginInfo = input.nextLine().split(" ");
		
		while (!(loginInfo.length == 2) && !(loginInfo.length == 3)) {
			System.out.println("Incorrect number of arguments, please read the above instructions.");
			loginInfo = input.nextLine().split(" ");
		}
		
		return loginInfo;
	}
	
	private static int signIn(String[] loginInfo, char userTag) throws SQLException {		
		// Gets taxID from user table by signing in.
		if (loginInfo.length == 3) {
			return User.register(loginInfo[0], loginInfo[1], userTag);
		}
		else if (loginInfo.length == 2) {
			return User.login(loginInfo[0], loginInfo[1], userTag);
		}
		else {
			System.out.println("ERROR: Should never enter here.");
		}

		return -1;
	}
	
	// NOT DONE
	private static void takeCommandAsManager() {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("---COMMANDS---");
			System.out.println("Deposit: deposit <amount>");
			System.out.println("Withdraw: withdraw <amount>");
			System.out.println("Buy: buy <number of shares> <stock symbol>");
			System.out.println("Sell: sell <number of shares> <stock symbol> <original buy price>");
			System.out.println("Show Balance: balance");
			System.out.println("Transaction history: history");
			System.out.println("Stock Info: <stock symbol>");
			System.out.println("Movie Info: <movie name> <filter (top or reviews)>");
			
			String[] command = input.nextLine().toLowerCase().split(" ");
			String action = command[0];
			String[] arguments = Arrays.copyOfRange(command, 1, command.length);
			// TODO: Add commands.
			break;
		}
	}
	
	// NOT DONE
	private static void takeCommandAsTrader() {
		
	}
	
	// NOT DONE
	private static void takeCommandAsOperator() {
		
	}

}
