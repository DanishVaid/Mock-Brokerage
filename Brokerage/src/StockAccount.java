import com.sun.xml.internal.bind.v2.model.annotation.Quick;

public class StockAccount {
	
	public static boolean buy(double numShares, String stockSymbol, double stock_price) {
		// TODO: Figure out how to buy
		String insert_part = "INSERT INTO stock_accounts (tax_id, stock_sym, num_shares, date, type, price)";
		String values_part = String.format("VALUES (%d, %s, %.3f, '12/2/2017', 'buy', %.2f)", User.currentTaxID, stockSymbol, numShares, CommandUI.currentDate, stock_price);

		String query = insert_part + " " + values_part + ";";
		JDBC.statement.executeUpdate(query);
		return true;
	}
	
	public static boolean sell(double numShares, String stockSymbol) {
		// TODO: Figure out how to sell
		String query = String.format("SELECT SUM(num_shares) FROM stock_accounts WHERE tax_id = %d AND stock_sym = %s;", User.currentTaxID, stockSymbol);
		ResultSet result = JDBC.statement.executeUpdate(query);

		if(result.next()){
			if(result.getDouble(1) >= numShares){
				String insert_part = "INSERT INTO stock_accounts (tax_id, stock_sym, num_shares, date, type, price)";
				String values_part = String.format("VALUES (%d, %s, %.3f, '12/2/2017', 'buy', %.2f)", User.currentTaxID, stockSymbol, (-1 * numShares), CommandUI.currentDate, Stock.getStockPrice(stockSymbol));
		
				query = insert_part + " " + values_part + ";";
				JDBC.statement.executeUpdate(query);
				return true;
			}
			else {
				System.out.println(String.format("You do not own enough shares of %s, aborting sale.", stockSymbol));
			}
		}
		else {
			// Do not own stocks
			System.out.println(String.format("You do not own any shares of %s, sale not possible", stockSymbol));
		}

		return false;
	}
	
}
