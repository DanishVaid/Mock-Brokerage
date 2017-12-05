import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
	
	public static String getMovieInfo(String movieName) throws SQLException {
		String movieInfo = "";
		
		String query = String.format("SELECT * FROM Movies WHERE name = '%s'", movieName);
		ResultSet result = JDBC.movieStatement.executeQuery(query);
		result.first();
		
		// TODO: Get attributes and store into movieInfo string.
		
		
		return movieInfo;
	}

	public static String getTopMovies(int beginYear, int endYear) throws SQLException {
		String topMovies = "";
		
		String query = String.format("SELECT * FROM movies WHERE year > %d AND rating > 5", beginYear);	//TODO: cross join with endYear, also where rating > 5
		ResultSet result = JDBC.statement.executeQuery(query);
		
		while (result.next()) {
			// TODO: Get attributes and store into string.
			
			// TODO: Append string to topMovies.
			
		}
		
		return topMovies;
	}

	public static String getMovieReviews(String movieName) throws SQLException {
		String movieReviews = "";
		
		String query = String.format("SELECT reviews FROM movies WHERE name = %s", movieName); // TODO: Look up reviews formatting.
		ResultSet result = JDBC.statement.executeQuery(query);
		result.first();
		
		// TODO: Get attributes and store into movieInfo string.
		
		
		return movieReviews;
	}
}
