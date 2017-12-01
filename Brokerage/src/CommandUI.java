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
			// int x = 1 / 0;

			// Manager commands and actions.
			if (userType.equals("manager")) {
				if(!signIn(loginInfo, 'm')){
					System.out.println("--- System Error Logging in");
					throw new Exception();
				}
				System.out.println("Logged in successful.");
				takeCommandAsManager();
			}
			// Trader commands and actions.
			else if (userType.equals("trader")) {
				if(!signIn(loginInfo, 't')){
					System.out.println("--- System Error Logging in");
					throw new Exception();
				}
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
	
	private static boolean signIn(String[] loginInfo, char userTag) throws SQLException {		
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

		return false;
	}
	
	// NOT DONE
	private static void takeCommandAsManager() throws SQLException {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("---COMMANDS---");
			System.out.println("Add Interest:				add_interest 		<interest rate>");
			System.out.println("Generate Monthly Statement:	monthly_statement	<customer taxID>");
			System.out.println("List Active Customers:		active_customers");
			System.out.println("Generate DTER:				dter");
			System.out.println("Customer Report:			customer_report 	<customer taxID>");
			System.out.println("Delete Transactions:		delete_transactions");
			System.out.println("Exit System:				exit");
			
			String[] command = input.nextLine().toLowerCase().split(" ");
			String action = command[0];
			String[] arguments = Arrays.copyOfRange(command, 1, command.length);
			// TODO: Add commands.
			
			if (action.equals("add_interest")) {
				if (arguments.length == 1) {
					// Arguments = interestRate
					// Manager.addInterest(arguments[0]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("monthly_statement")) {
				if (arguments.length == 1) {
					// Arguments = taxID
					// Manager.generateMonthlyStatement(arguments[0]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("active_customers")) {
				if (arguments.length == 0) {	// TODO: Check if this is a good condition statement, copyOfRange function might return a null, rather than an array of 0 length.
					// Manager.listActiveCustomers();
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("dter")) {
				if (arguments.length == 0) {
					// Manager.generateDTER();
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("customer_report")) {
				if (arguments.length == 1) {
					// Arguments: taxID
					// Manager.generateCustomerReport(arguments[0]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("delete_transactions")) {
				if (arguments.length == 0) {
					// Manager.deleteTransactions();
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			else {
				System.out.println("Command not recognized.");
				break;
			}
		}
	}
	
	// NOT DONE
	private static void takeCommandAsTrader() throws SQLException {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("---COMMANDS---");
			System.out.println("Deposit:				deposit				<amount>");
			System.out.println("Withdraw:				withdraw			<amount>");
			System.out.println("Buy:					buy					<number of shares> <stock symbol>");
			System.out.println("Sell:					sell				<number of shares> <stock symbol> <original buy price>");
			System.out.println("Show Balance:			balance");
			System.out.println("Transaction history:	transaction_history");
			System.out.println("Stock Info:				stock_info			<stock symbol>");
			System.out.println("Movie Info:				movie_info			<movie name OR filter (top or reviews)>");
			System.out.println("Exit System:			exit");
			
			String[] command = input.nextLine().toLowerCase().split(" ");
			String action = command[0];
			String[] arguments = Arrays.copyOfRange(command, 1, command.length);
			
			if (action.equals("deposit")) {
				try {
					// Arguments: taxID, amount
					MarketAccount.deposit(User.currentTaxID, Double.parseDouble(arguments[0]));
				}
				catch (IllegalArgumentException e) {
					System.out.println("Invalid argument types. Please try again.");
				}
				catch (IndexOutOfBoundsException e) {
					System.out.println("Invalid number of arguments. Please try again.");
				}
			}
			else if (action.equals("withdraw")) {
				if (arguments.length == 1) {
					// Arguments: taxID, amount
					// Trader.withdraw(taxID, arguments[0]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("buy")) {
				if (arguments.length == 2) {
					// Arguments: taxID, numShares, stockSymbol
					// Trader.buy(taxID, arguments[0], arguments[1]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("sell")) {
				if (arguments.length == 3) {
					// Arguments: taxID, numShares, stockSymbol, buyPrice
					// Trader.sell(taxID, arguments[0], arguments[1], arguments[2]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("balance")) {
				if (arguments.length == 0) {
					// Arguments: taxID
					// Trader.balance(taxID);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("transaction_history")) {
				if (arguments.length == 0) {
					// Arguments: taxID
					// Trader.transactionHistory(taxID);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("stock_info")) {
				if (arguments.length == 1) {
					// Arguments: stockSymbol
					// Stock.getStockInfo(arguments[0]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("movie_info")) {
				if (arguments.length == 1) {
					// Arguments: movieInquiry
					// Movie.getMovieInfo(arguments[0]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			else {
				System.out.println("Command not recognized.");
			}
		}
	}
	
	// NOT DONE
	private static void takeCommandAsOperator() {
		while (true) {
			System.out.println("What would you like to do?");
			System.out.println("---COMMANDS---");
			System.out.println("Open Market:		open_market");
			System.out.println("Close Market:		close_market");
			System.out.println("Set Stock Price:	set_stock_price	<stock symbol> <price>");
			System.out.println("Set Date:			set_date		<month> <day> <year>");
			System.out.println("Exit System:		exit");
			
			String[] command = input.nextLine().toLowerCase().split(" ");
			String action = command[0];
			String[] arguments = Arrays.copyOfRange(command, 1, command.length);
			// TODO: Add commands.
			
			if (action.equals("open_market")) {
				if (arguments.length == 0) {
					Operator.openMarket();
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("close_market")) {
				if (arguments.length == 0) {
					Operator.closeMarket();
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("set_stock_price")) {
				if (arguments.length == 2) {
					// Arguments: stockSymbol, price
					// Operator.setStockPrice(arguments[0], arguments[1]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("set_date")) {
				if (arguments.length == 3) {
					// Arguments: month, day, year
					// Operator.setDate(arguments[0], arguments[1], arguments[2]);
				}
				else {
					System.out.println("Invalid number of arguments.");
				}
			}
			else if (action.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			else {
				System.out.println("Command not recognized.");
			}
		}
	}

}
