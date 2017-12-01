import java.sql.SQLException;

public class Trader extends User {
	
	/* COMMANDS */
	public static void deposit(int taxID, double amount) throws SQLException {
		MarketAccount.deposit(taxID, amount);
	}
	
	public static void withdraw(int taxID, double amount) throws SQLException {
		// TODO: From market or stock account? Or is there one balance?
		// Probably market, stock balance is different, not money but amount of stocks
		MarketAccount.withdraw(taxID, amount);
	}
	
	public static void buy(int taxID, double numShares, String stockSymbol) {
		double stockPrice = Stock.getStockPrice(stockSymbol);
		double value = stockPrice * numShares;
		
		double currentBalance = MarketAccount.getBalance(taxID);
		if (currentBalance <= value) {
			System.out.println("You do not have enough money to make this transaction.");
			return;
		}

		MarketAccount.buy(taxID, value);
		StockAccount.buy(taxID, numShares, stockSymbol);	// TODO: Is it possible to not be able to buy? Not enough stock on market?
		
		System.out.println(String.format("Successful purchase of: %s", stockSymbol));
	}
	
	public static void sell(int taxID, double numShares, String stockSymbol, double buyPrice) {
		double stockPrice = Stock.getStockPrice(stockSymbol);
		double value = stockPrice * numShares;
		
		StockAccount.sell(taxID, numShares, stockSymbol, buyPrice);	// TODO: Function can return boolean if possible to sell?
		MarketAccount.sell(taxID, value);
		
		System.out.println(String.format("Successful sell of: %s", stockSymbol));
	}
	
	public static void showBalance(int taxID) {
		double balance = MarketAccount.getBalance(taxID);
		
		System.out.println(String.format("Your current balance is: %f", balance));
	}
	
	public static void showTransactionHistory(int taxID) {
		String transactionHistory = Transactions.getTransactionHistory(taxID);
		
		System.out.println("Here are your previous transactions: ");
		System.out.println(transactionHistory);
	}

	public static void showStockPrice(String stockSymbol) {
		double stockPrice = Stock.getStockPrice(stockSymbol);
		
		System.out.println(String.format("The current price of %s is %d.", stockSymbol, stockPrice));
	}
	
	public static void showMovieInfo(String movieInquiry) {	// TODO: Update parameters
		String movieInfo = "";
		if (movieInquiry.equals("top")) {
			System.out.println("Please enter <begin date> <end date>.");
			String[] timeInterval = CommandUI.input.nextLine().split(" ");
			
			movieInfo = Movie.getTopMovies(timeInterval[0], timeInterval[1]);
			
			System.out.println("Here are the top movies for the given time interval:");
		}
		else if (movieInquiry.equals("reviews")) {
			movieInfo = Movie.getMovieReviews(movieInquiry);
			
			System.out.println("Here are the reviews for the given movie:");
		}
		else {
			movieInfo = Movie.getMovieInfo(movieInquiry);
			
			System.out.println("Here is the information for the given movie:");
		}
		
		System.out.println(movieInfo);
	}

}
