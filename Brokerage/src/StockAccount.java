import java.sql.ResultSet;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;

public class StockAccount {
	
	public static boolean buy(double numShares, String stockSymbol) throws SQLException{
		String insert_part = "INSERT INTO stock_accounts (tax_id, stock_sym, num_shares, date, type, price, earnings)";
		String values_part = String.format("VALUES (%d, '%s', %.3f, '%s', 'buy', %.2f, 0.0)", User.currentTaxID, stockSymbol, numShares, CommandUI.currentDate.toString(), Stock.getStockPrice(stockSymbol));

		String query = insert_part + " " + values_part + ";";
		JDBC.statement.executeUpdate(query);
		return true;
	}
	
	public static boolean sell(double numShares, String stockSymbol, double buyPrice) throws SQLException{
		String query = String.format("SELECT SUM(num_shares) FROM stock_accounts WHERE tax_id = %d AND stock_sym = '%s' AND price = %.2f;", User.currentTaxID, stockSymbol, buyPrice);
		ResultSet result = JDBC.statement.executeQuery(query);

		if(result.next()){
			if(result.getDouble(1) >= numShares){
				double current_price = Stock.getStockPrice(stockSymbol);
				double earnings = (current_price - buyPrice) * numShares;
				String insert_part = "INSERT INTO stock_accounts (tax_id, stock_sym, num_shares, date, type, price, earnings)";
				String values_part = String.format("VALUES (%d, '%s', %.3f, '%s', 'sell', %.2f, %.2f)", User.currentTaxID, stockSymbol, (-1 * numShares), CommandUI.currentDate, buyPrice, earnings);

				query = insert_part + " " + values_part + ";";
				JDBC.statement.executeUpdate(query);
				return true;
			}
			else {
				System.out.println(String.format("You do not own enough shares of '%s' at price: $%.2f, aborting sale.", stockSymbol, buyPrice));
			}
		}
		else {
			// Do not own stocks
			System.out.println(String.format("You do not own any shares of '%s' at price: $%.2f, sale not possible", stockSymbol, buyPrice));
		}

		return false;
	}
	
}
