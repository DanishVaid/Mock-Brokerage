import java.io.BufferedReader;
import java.io.File;
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
		File directory = new File(System.getProperty("user.dir"));
	    directory = directory.getParentFile();
	    directory = directory.getParentFile();
	    File file = new File(directory,"credentials.json");
		String credentialsJSON = getCredentialJSON(file);
		
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
	
	private static String getCredentialJSON(File file) {
		String JSONContent = "";

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));

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