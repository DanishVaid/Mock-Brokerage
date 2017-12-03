import java.sql.SQLException;

public class Trader extends User {
	
	/*** COMMANDS ***/
	
	public static void deposit(double amount) throws SQLException {
		MarketAccount.deposit(amount);
		
		System.out.println(String.format("Successfully deposited $%.2f.", amount));
	}
	
	public static void withdraw(double amount) throws SQLException {
		MarketAccount.withdraw(amount);
		
		System.out.println(String.format("Successfully withdrew $%.2f.", amount));
	}
	
	public static void buy(double numShares, String stockSymbol) throws SQLException {
		double stockPrice;
		try {
			stockPrice = Stock.getStockPrice(stockSymbol);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Stock Symbol not recognized");
			return;
		}

		double value = stockPrice * numShares;
		
		if (MarketAccount.buy(value)){
			StockAccount.buy(numShares, stockSymbol);
			System.out.println(String.format("Successfully purchased %.3f '%s' shares for $%.2f.", numShares, stockSymbol, value));
			return;
		}
		System.out.println(String.format("Failure to purchase %d shares of '%s'.", numShares, stockSymbol));
	}
	
	public static void sell(double numShares, String stockSymbol, double buyPrice) throws SQLException {
		double stockPrice;
		try {
			stockPrice = Stock.getStockPrice(stockSymbol);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Stock Symbol not recognized");
			return;
		}

		double value = stockPrice * numShares;
		
		if(StockAccount.sell(numShares, stockSymbol, buyPrice)) {
			MarketAccount.sell(value);
			System.out.println(String.format("Successfully sold %.3f '%s' shares for $%.2f.", numShares, stockSymbol, value));
			return;
		}
		System.out.println(String.format("Failure to sell %.3f shares of '%s' bought at %.2f.", numShares, stockSymbol, buyPrice));
	}
	
	public static void showBalance() throws SQLException {
		double balance = MarketAccount.getBalance();
		
		System.out.println(String.format("Your current balance is: %f", balance));
	}
	
	public static void showTransactionHistory() throws SQLException {
		String transactionHistory = Transactions.getTransactionHistory();
		
		System.out.println("Here are your previous transactions: ");
		System.out.println(transactionHistory);
	}

	public static void showStockPrice(String stockSymbol) throws SQLException {
		double stockPrice = Stock.getStockPrice(stockSymbol);
		
		System.out.println(String.format("The current price of %s is %d.", stockSymbol, stockPrice));
	}
	
	public static void showMovieInfo(String movieInquiry) throws SQLException {
		String movieInfo = Movie.getMovieInfo(movieInquiry);
			
		System.out.println("Here is the information for the given movie:");
		System.out.println(movieInfo);
	}

	public static void showTopMovies(int beginYear, int endYear) throws SQLException {
		String topMovies = Movie.getTopMovies(beginYear, endYear);
		
		System.out.println("Here are the top movies for the given time interval:");
		System.out.println(topMovies);
	}

	public static void showMovieReviews(String movieName) throws SQLException {
		String movieReviews = Movie.getMovieReviews(movieName);
		
		System.out.println("Here are the reviews of the given movie: ");
		System.out.println(movieReviews);
	}

	public static void showOwningStocks() throws SQLException {
		// TO DO if desired: show net stocks owned
	}

}
