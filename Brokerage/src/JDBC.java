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

	private static String host;
	private static String hostMovie;
	private static String username;
	private static String password;
	
	public static Connection connection;
	public static Statement statement;

	public static Connection movieConnection;
	public static Statement movieStatement;
	
	
	public static void setup() throws ClassNotFoundException, SQLException {
		setCredentials();

		Class.forName("com.mysql.jdbc.Driver");
		
		connection = DriverManager.getConnection(host, username, password);
		statement = connection.createStatement();
		movieConnection = DriverManager.getConnection(hostMovie, username, password);
		movieStatement = movieConnection.createStatement();
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
		// Find directory of credentials.json.
		File directory = new File(System.getProperty("user.dir"));
	    directory = directory.getParentFile();
	    directory = directory.getParentFile();
	    File file = new File(directory,"credentials.json");
		String credentialsJSON = getCredentialJSON(file);
		
		// Parse JSON for credential fields
		try {
			JSONObject credentials = new JSONObject(credentialsJSON);
			host = credentials.getString("HOST");
			hostMovie = credentials.getString("HOST_MOVIE");
			username = credentials.getString("USER");
			password = credentials.getString("PASS");
		}
		catch (JSONException e) {
			System.out.println("JSON Exception thrown.");
			e.printStackTrace();
		}
	}
	
	/*** PRIVATE METHODS ***/
	
	private static String getCredentialJSON(File file) {
		String JSONContent = "";

		BufferedReader bufferedReader = null;
		try {
			bufferedReader = new BufferedReader(new FileReader(file));

			// Read in CSIL DB credentials into JSON format.
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