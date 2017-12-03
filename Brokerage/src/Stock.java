import java.sql.ResultSet;
import java.sql.SQLException;

public class Stock {
	
	public static ResultSet getAllStockInfo() {
		
		return null;
	}

	public static ResultSet getStockInfo(String stockSymbol) {
		
		return null;
	}
	
	public static double getStockPrice(String stockSymbol) throws SQLException, IllegalArgumentException {
		String query = String.format("SELECT current_price FROM actor_stocks WHERE stock_sym = '%s'", stockSymbol);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		if(result.next()){
			return result.getInt("current_price");
		}

		throw new IllegalArgumentException();
	}

	public static void setStockPrice(String stockSymbol, double newPrice) throws SQLException {
		String query = String.format("UPDATE ...", stockSymbol, newPrice);	// TODO: Finish query.
		
		JDBC.statement.executeUpdate(query);
	}
}
