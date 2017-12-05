import java.sql.ResultSet;
import java.sql.SQLException;

public class Trader extends User {
	
	/*** COMMANDS ***/
	
	public static void deposit(double amount) throws SQLException {
		MarketAccount.deposit(amount);
		Transactions.addDepositRecord(amount);
		System.out.println(String.format("Successfully deposited $%.2f.", amount));
	}
	
	public static void withdraw(double amount) throws SQLException {
		MarketAccount.withdraw(amount);
		Transactions.addWithdrawRecord(amount);
		System.out.println(String.format("Successfully withdrew $%.2f.", amount));
	}
	
	public static void buy(double numShares, String stockSymbol) throws SQLException {
		if(!Operator.canTrade){
			System.out.println("You may not trade: the market is currently closed\n");
			return;
		}

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
			Transactions.addBuyRecord(stockSymbol, stockPrice, numShares);
			return;
		}
		System.out.println(String.format("Failure to purchase %d shares of '%s'.", numShares, stockSymbol));
	}
	
	public static void sell(double numShares, String stockSymbol, double buyPrice) throws SQLException {
		if(!Operator.canTrade){
			System.out.println("You may not trade: the market is currently closed\n");
			return;
		}

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
			// addSellRecord(String stockSym, double currentStockPrice, double stockBoughtAt, double numOfShares)
			Transactions.addSellRecord(stockSymbol, buyPrice, numShares);
			return;
		}
		System.out.println(String.format("Failure to sell %.3f shares of '%s' bought at %.2f.", numShares, stockSymbol, buyPrice));
	}
	
	public static void showBalance() throws SQLException {
		double balance = MarketAccount.getBalance();
		
		System.out.println(String.format("Your current balance is: %.2f", balance));
	}
	
	public static void showTransactionHistory() throws SQLException {
		String transactionHistory = Transactions.showTransactionHistory();
		
		System.out.println("Here are your previous transactions: ");
		System.out.println(transactionHistory);
	}

	public static void showStockPrice(String stockSymbol) throws SQLException {
		double stockPrice;
		try{
			stockPrice = Stock.getStockPrice(stockSymbol);
		}
		catch(IllegalArgumentException e) {
			System.out.println("Stock Symbol not recognized");
			return;
		} 
		
		System.out.println(String.format("The current price of %s is $%.2f.", stockSymbol, stockPrice));
	}

	public static void showActorProfile(String stockSymbol) throws SQLException {
		String query = String.format("SELECT * FROM actor_stocks LEFT JOIN contracts ON actor_stocks.stock_sym = contracts.stock_sym WHERE actor_stocks.stock_sym = '%s';", stockSymbol);
		ResultSet result = JDBC.statement.executeQuery(query);
		System.out.println("------------------ Actor Profile Summary -------------------");

		if(result.first()){
			System.out.println(String.format("%-7s %-15s %-25s %s", "Stock", "Name", "DoB", "Current Stock Price"));
			System.out.println(String.format("%-7s %-15s %-25s %.2f", result.getString("stock_sym"), result.getString("actor_name") , result.getString("dob"), result.getDouble("current_price")));
			System.out.println("Contract(s) Information:");
			System.out.println(String.format("%-5s %-25s %-8s %-8s %s", "", "Movie Title", "Role", "Year", "Value"));
		}
		else {
			System.out.println("No actor with that stock symbol");
			return;
		}

		do {
			if(result.getString("movie_title") != null){
				System.out.println(String.format("%-5s %-25s %-8s %-8d %.2f", "", result.getString("movie_title"), result.getString("role"), result.getInt("year"), result.getDouble("value")));
			}
		} while(result.next());

		System.out.println("------------------ Actor Profile Completed -------------------");
	}

	// TODO:
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
		// TODO: if desired: show net stocks owned

	}

}
