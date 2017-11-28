import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONException;
import org.json.JSONObject;

public class JDBC {

	public static String host;
	public static String username;
	public static String password;
	
	public static Connection connection;
	public static Statement statement;
	
	
	public static void setup() throws ClassNotFoundException, SQLException {
		setCredentials();
		
		// TODO: What does this do?
		Class.forName("com.mysql.jdbc.Driver");
		
		connection = DriverManager.getConnection(host, username, password);
		statement = connection.createStatement();
		
		// TODO: Continue from below

	}
	
	public static void endSession() throws SQLException {
		if (connection != null) {
			connection.close();
		}
		
		if (statement != null) {
			statement.close();
		}
	}
	
	public static void setCredentials() {
		String credentialsJSON = getCredentialJSON("././credentials.json");
		
		try {
			JSONObject credentials = new JSONObject(credentialsJSON);
			host = credentials.getString("HOST");
			username = credentials.getString("USER");
			password = credentials.getString("PASS");
		}
		catch (JSONException e) {
			System.out.println("JSON Exception thrown.");
			e.printStackTrace();
		}
	}
	
	private static String getCredentialJSON(String filename) {
		String JSONContent = "";

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(filename));

			String line = "";
			while ((line = bufferedReader.readLine()) != null) {
				JSONContent += line + "\n";
			}

			return JSONContent;
		}
		catch (IOException e) {
			System.out.println("IOException thrown.");
			e.printStackTrace();
			return null;
		}
		finally {
			try {
				bufferedReader.close();
			} 
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}




/*
import java.sql.*;

public class JdbcExample {

    public static final String HOST = "jdbc:mysql://cs174a.engr.ucsb.edu:3306/Test";
    public static final String USER = "";
    public static final String PWD = "";
    public static final String QUERY = "SELECT * from  TestTable";

    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {

            connection = DriverManager.getConnection(HOST, USER, PWD);

            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(QUERY);


            System.out.println("A\tB\tC");

            while (resultSet.next()) {
                String A = resultSet.getString("A");
                int B = resultSet.getInt("B");
                int C = resultSet.getInt("C");

                System.out.println(A + "\t" + B + "\t" + C);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();
            }
        }

    }
}
*/