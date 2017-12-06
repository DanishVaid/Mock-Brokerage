import java.sql.ResultSet;
import java.sql.SQLException;

public class Movie {
	
	public static String getMovieInfo(String movieName) throws SQLException {
		String movieInfo = "";
		
		String query = String.format("SELECT * FROM Movies WHERE title = '%s'", movieName);
		ResultSet result = JDBC.movieStatement.executeQuery(query);		
		
		movieInfo += "------------------------ Movie Information -------------------------\n";
		movieInfo += String.format("%-5s %-50s %-8s %s\n", "", "Title", "Year", "Rating");
		if(!result.first()) {
			movieInfo += "No movie with this name.\n";
			return movieInfo;
		}

		do {
			movieInfo += String.format("%-5s %-50s %-8d %s\n", "", result.getString("title"), result.getInt("production_year"), result.getDouble("rating"));
		} while(result.next());
		movieInfo += "------------------------ Movie Information End ----------------------\n";
		return movieInfo;
	}

	public static String getTopMovies(int beginYear, int endYear) throws SQLException {
		String topMovies = "";
		
		String query = String.format("SELECT * FROM Movies WHERE production_year >= %d AND production_year <= %d AND rating >= 5", beginYear, endYear);
		ResultSet result = JDBC.movieStatement.executeQuery(query);
		
		topMovies += String.format("--------------------- Top Movie Information (%d - %d) ----------------------\n", beginYear, endYear);
		topMovies += String.format("%-5s %-50s %-8s %s\n", "", "Title", "Year", "Rating");

		if(!result.first()) {
			topMovies += "No top movies in this time period.\n";
			return topMovies;
		}

		do {
			topMovies += String.format("%-5s %-50s %-8d %s\n", "", result.getString("title"), result.getInt("production_year"), result.getDouble("rating"));
		} while(result.next());
		topMovies += String.format("---------------------- Top Movie Information End (%d - %d) --------------------\n", beginYear, endYear);
		
		return topMovies;
	}

	public static String getMovieReviews(String movieName) throws SQLException {
		String movieReviews = "";
		
		String query = String.format("SELECT author, review FROM Movies JOIN Reviews ON Movies.id = Reviews.movie_id WHERE Movies.title = '%s';", movieName);
		ResultSet result = JDBC.movieStatement.executeQuery(query);
		movieReviews += String.format("--------------------- Movie Reviews (%s) ----------------------\n", movieName);
		movieReviews += String.format("%-5s %-13s %-25s\n", "", "Author", "Review");

		if(!result.first()) {
			movieReviews += "No Reviews for this movie.\n";
			return movieReviews;
		}
		
		do {
			movieReviews += String.format("%-5s %-13s %-25s\n", "", result.getString("author"), result.getString("review"));
		} while(result.next());
		movieReviews += String.format("---------------------- Movie Reviews End (%s) --------------------\n", movieName);
		
		return movieReviews;
	}
}
