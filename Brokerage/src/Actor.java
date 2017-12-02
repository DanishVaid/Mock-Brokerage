import java.sql.ResultSet;
import java.sql.SQLException;

public class Actor {
	
	public static ResultSet getActorInfo() throws SQLException {
		String query = "";
		
		return JDBC.statement.executeQuery(query);
	}
	
}
