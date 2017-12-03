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
		double stockPrice = Stock.getStockPrice(stockSymbol);
		double value = stockPrice * numShares;
		
		double currentBalance = MarketAccount.getBalance();
		if (currentBalance <= value) {
			System.out.println("You do not have enough money to make this transaction.");
			return;
		}

		MarketAccount.buy(value);
		StockAccount.buy(numShares, stockSymbol);	// TODO: Is it possible to not be able to buy? Not enough stock on market?
		
		System.out.println(String.format("Successfully purchased of: %s", stockSymbol));
	}
	
	public static void sell(double numShares, String stockSymbol, double buyPrice) throws SQLException {
		double stockPrice = Stock.getStockPrice(stockSymbol);
		double value = stockPrice * numShares;
		
		StockAccount.sell(numShares, stockSymbol, buyPrice);	// TODO: Function can return boolean if possible to sell?
		MarketAccount.sell(value);
		
		System.out.println(String.format("Successfully sold of: %s", stockSymbol));
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

}
