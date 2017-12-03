import java.sql.ResultSet;
import java.sql.SQLException;

public class Stock {
	
	public static ResultSet getAllStockInfo() {
		
		return null;
	}

	public static ResultSet getStockInfo(String stockSymbol) {
		
		return null;
	}
	
	public static double getStockPrice(String stockSymbol) throws SQLException {
		String query = String.format("SELECT price FROM stocks WHERE stock_symbol = %s", stockSymbol);
		
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		
		return result.getInt("price");
	}

	public static void setStockPrice(String stockSymbol, double newPrice) throws SQLException {
		String query = String.format("UPDATE ...", stockSymbol, newPrice);	// TODO: Finish query.
		
		JDBC.statement.executeUpdate(query);
	}
}
