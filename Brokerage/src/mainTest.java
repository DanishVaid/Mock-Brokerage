import java.sql.SQLException;

public class mainTest {

	public static void main(String[] args) {
		try {
			JDBC.setup();
			// Operator.initializeDB();
			Operator.resetDB();
		}
		catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally {
			try {
				JDBC.endSession();
			}
			catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
