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
			Operator.setSystem();
			// int x = 1 / 0;

			// Manager commands and actions.
			if (userType.equals("manager")) {
				if(!signIn(loginInfo, 'm')){
					System.out.println("--- System Error Logging in");
					throw new Exception();
				}
				System.out.println("Logged in successfully.");
				takeCommandAsManager();
			}
			// Trader commands and actions.
			else if (userType.equals("trader")) {
				if(!signIn(loginInfo, 't')){
					System.out.println("--- System Error Logging in");
					throw new Exception();
				}
				System.out.println("Logged in successfully.");
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
	
	/*** PRIVATE METHODS ***/
	
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
			
			try {
				if (action.equals("add_interest")) {
					double interestRate = Double.parseDouble(arguments[0]);
					Manager.addInterest(interestRate);
				}
				else if (action.equals("monthly_statement")) {
					int customerTaxID = Integer.parseInt(arguments[0]);
					Manager.generateMonthlyStatement(customerTaxID);
				}
				else if (action.equals("active_customers")) {
					Manager.listActiveCustomers();
				}
				else if (action.equals("dter")) {
					Manager.generateDTER();
				}
				else if (action.equals("customer_report")) {
					int customerTaxID = Integer.parseInt(arguments[0]);
					Manager.generateCustomerReport(customerTaxID);
				}
				else if (action.equals("delete_transactions")) {
					Manager.deleteTransactions();
				}
				else if (action.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				}
				else {
					System.out.println("Command not recognized.");
				}
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid number of arguments.");
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid argument type(s).");
			}
		}
	}
	
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
			System.out.println("Stock Price:			stock_price			<stock symbol>");
			System.out.println("Movie Info:				movie_info			<movie name>");
			System.out.println("Top Movies:				top_movies			<begin year> <end year>");
			System.out.println("Movie Reviews:			movie_reviews		<movie name>");	
			System.out.println("Exit System:			exit");
			
			String[] command = input.nextLine().toLowerCase().split(" ");
			String action = command[0];
			String[] arguments = Arrays.copyOfRange(command, 1, command.length);
			
			try {
				if (action.equals("deposit")) {
					double amount = Double.parseDouble(arguments[0]);
					Trader.deposit(amount);
				}
				else if (action.equals("withdraw")) {
					double amount = Double.parseDouble(arguments[0]);
					Trader.withdraw(amount);
				}
				else if (action.equals("buy")) {
					double numShares = Double.parseDouble(arguments[0]);
					String stockSymbol = arguments[1].toUpperCase();
					Trader.buy(numShares, stockSymbol);
				}
				else if (action.equals("sell")) {
					double numShares = Double.parseDouble(arguments[0]);
					String stockSymbol = arguments[1].toUpperCase();
					double buyPrice = Double.parseDouble(arguments[2]);
					Trader.sell(numShares, stockSymbol, buyPrice);
				}
				else if (action.equals("balance")) {
					Trader.showBalance();
				}
				else if (action.equals("transaction_history")) {
					Trader.showTransactionHistory();
				}
				else if (action.equals("stock_price")) {
					String stockSymbol = arguments[0];
					Trader.showStockPrice(stockSymbol);
				}
				else if (action.equals("movie_info")) {
					String movieName = arguments[0];
					Trader.showMovieInfo(movieName);
				}
				else if (action.equals("top_movies")) {
					int beginYear = Integer.parseInt(arguments[0]);
					int endYear = Integer.parseInt(arguments[1]);
					Trader.showTopMovies(beginYear, endYear);
				}
				else if (action.equals("movie_reviews")) {
					String movieName = arguments[0];
					Trader.showMovieReviews(movieName);
				}
				else if (action.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				}
				else {
					System.out.println("Command not recognized.");
				}
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid number of arguments.");
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid argument type(s).");
			}
		}
	}
	
	private static void takeCommandAsOperator() throws SQLException {
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

			try {
				if (action.equals("open_market")) {
					Operator.openMarket();
				}
				else if (action.equals("close_market")) {
					Operator.closeMarket();
				}
				else if (action.equals("set_stock_price")) {
					String stockSymbol = arguments[0];
					double price = Double.parseDouble(arguments[1]);
					Operator.setStockPrice(stockSymbol, price);
				}
				else if (action.equals("set_date")) {
					int month = Integer.parseInt(arguments[0]);
					int day = Integer.parseInt(arguments[1]);
					int year = Integer.parseInt(arguments[2]);
					Operator.setDate(month, day, year);
				}
				else if (action.equals("exit")) {
					System.out.println("Goodbye!");
					break;
				}
				else {
					System.out.println("Command not recognized.");
				}
			}
			catch (IndexOutOfBoundsException e) {
				System.out.println("Invalid number of arguments.");
			}
			catch (NumberFormatException e) {
				System.out.println("Invalid argument type(s).");
			}
		}
	}
}
