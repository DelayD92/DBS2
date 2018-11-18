package de.hsh.dbs2.ue3.iter3.factory;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.model.Movie;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public final class MovieFactory {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static Movie insert(String title, int year, char type) throws Exception {
        Movie movie = new Movie(title, year, type);
        movie.insert();
        return movie;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static Movie findById(Long movieId) throws Exception {
        Log.debug(String.format("Looking for a movie with the Id '%d'", movieId));

        if (movieId == null || movieId <= 0) {
            Log.debug(String.format("Invalid Movie Id '%d'", movieId));
            return null;
        }

        String movieQuery = new StringBuilder()
            .append("SELECT Title, Year, Type")
            .append(" FROM Movie")
            .append(" WHERE Id = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setLong(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.debug(String.format("No movie found with the Id '%d'", movieId));
                return null;
            }

            Movie movie = new Movie(movieId,
                rs.getString("Title"),
                rs.getInt("Year"),
                rs.getString("Type").charAt(0));

            Log.debug(String.format("Movie record found with the Id '%d'", movieId), "Movie: ", movie);

            return movie;
        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }
    }

    public static List<Movie> findByTitle(String title) throws Exception {
        return findByTitle(title, false);
    }

    public static List<Movie> findByTitle(String title, boolean globalSearch) throws Exception {
        List<Movie> results = new ArrayList<>();

        if(title == null || title.length() == 0) {
            Log.debug("Title is empty. Nothing to do. Returning...");
            return results;
        }

        Log.debug(String.format("Looking for Movie records with the title '%s'", title));

        StringBuilder queryTmp = new StringBuilder()
            .append("SELECT Id, Title, Year, Type")
            .append(" FROM Movie");
        if(globalSearch) {
            queryTmp.append(" WHERE UPPER(Title) LIKE '%' || UPPER(?) || '%' ");
        } else {
            queryTmp.append(" WHERE title = ?");
        }

        String movieQuery = queryTmp.toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setString(1, title);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.debug(String.format("No movie found with the title '%s'", title));
                return results;
            }

            do {
                Movie movie = new Movie(rs.getLong("Id"),
                    rs.getString("Title"),
                    rs.getInt("Year"),
                    rs.getString("Type").charAt(0));
                results.add(movie);
            } while (rs.next());

            Log.debug(String.format("%d movie record(s) found with the title '%s'", results.size(), title), "Movies: ", results);
            return results;

        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean update(Movie movie, String title, int year, char type) throws Exception {
        movie.setTitle(title);
        movie.setYear(year);
        movie.setType(type);
        return movie.update();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean deleteById(Long movieId) throws Exception {
        Log.debug(String.format("Deleting Movie record with the Id '%d'", movieId));

        if (movieId == null || movieId <= 0) {
            Log.debug(String.format("Invalid Movie Id '%d'", movieId));
            return false;
        }

        String movieDeleteQuery = new StringBuilder()
            .append("DELETE FROM Movie")
            .append(" WHERE Id = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieDeleteQuery)) {
            stmt.setLong(1, movieId);
            int affectedRows = stmt.executeUpdate();
            if(affectedRows != 0) {
                Log.debug(String.format("Movie record with the Id '%d' deleted", movieId));
                return true;
            } else {
                Log.error(String.format("Deletion of Movie record with the Id '%d' failed.", movieId));
                return false;
            }
        } catch (SQLException e) {
            Log.error(String.format("Deletion of Movie record with the Id '%d' failed.", movieId));
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
