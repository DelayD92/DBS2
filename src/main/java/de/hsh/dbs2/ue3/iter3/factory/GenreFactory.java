package de.hsh.dbs2.ue3.iter3.factory;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.model.Genre;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public final class GenreFactory {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static List<Genre> getAll() throws Exception {
        String genreQuery = new StringBuilder()
            .append("SELECT Id, Genre")
            .append(" From Genre")
            .append(" ORDER BY Genre")
            .toString();

        List<Genre> results = new ArrayList<>();
        try (Statement stmt = Database.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(genreQuery);

            if (!rs.next()) {
                return results;
            }

            do {
                results.add(new Genre(rs.getLong("Id"), rs.getString("Genre")));
            } while(rs.next());

            return results;

        }  catch (SQLException e) {
            Log.error(e);
            throw e;
        }

    }

    public static Genre findByGenre(String genreStr) throws Exception {
        Log.debug(String.format("Looking for a Genre with the genre '%s'", genreStr));

        String movieQuery = new StringBuilder()
            .append("SELECT Id, Genre")
            .append(" FROM Genre")
            .append(" WHERE Genre = ?")
            .toString();

        Genre genre = null;

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setString(1, genreStr);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.debug(String.format("No Genre found with the genre '%s'", genreStr));
                return null;
            }

            genre = new Genre(rs.getLong("Id"), rs.getString("Genre"));

            Log.debug(String.format("Genre record found with the genre '%s'", genreStr, genre));

            return genre;

        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }

    }

    public static List<Genre> findAllByMovieId(Long movieId) throws Exception {
        Log.debug(String.format("Looking for Genre records for the MovieId '%d'", movieId));

        String movieQuery = new StringBuilder()
            .append("SELECT G.Id, G.Genre")
            .append(" FROM Genre G")
            .append(" JOIN MovieGenre MG ON MG.GenreId = G.Id")
            .append(" WHERE MG.MovieId = ?")
            .toString();

        List<Genre> results = new ArrayList<>();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setLong(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.debug(String.format("No Genre found for the MovieId '%d'", movieId));
                return results;
            }

            do {
                results.add(new Genre(
                    rs.getLong("Id"),
                    rs.getString("Genre")
                ));
            } while(rs.next());

            Log.debug(String.format("%d Genre record(s) found for the MovieId '%d'", results.size(), movieId));

            return results;

        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
