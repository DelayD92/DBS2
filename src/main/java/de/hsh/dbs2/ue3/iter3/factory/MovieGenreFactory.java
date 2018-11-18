package de.hsh.dbs2.ue3.iter3.factory;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.model.MovieGenre;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieGenreFactory {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static MovieGenre insert(Long movieId, Long genreId) throws Exception {
        MovieGenre mg = new MovieGenre(movieId, genreId);
        mg.insert();
        return mg;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static List<MovieGenre> findAllByMovieId(Long movieId) throws Exception {
        Log.debug(String.format("Looking for MovieGenre records with the MovieId '%d'", movieId));

        String movieQuery = new StringBuilder()
            .append("SELECT MovieId, GenreId")
            .append(" FROM MovieGenre")
            .append(" WHERE MovieId = ?")
            .toString();

        List<MovieGenre> results = new ArrayList<>();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setLong(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.debug(String.format("No MovieGenre found with the MovieId '%d'", movieId));
                return results;
            }

            do {
                results.add(new MovieGenre(
                    rs.getLong("MovieId"),
                    rs.getLong("GenreId")
                ));

            } while(rs.next());

            Log.debug(String.format("%d MovieGenre record(s) found with the MovieId '%d'", results.size(), movieId));

            return results;
        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean deleteByMovieId(Long movieId) throws Exception {
        Log.debug(String.format("Deleting MovieGenre record with the MovieId '%d'", movieId));

        if (movieId == null || movieId <= 0) {
            Log.debug(String.format("Invalid MovieId '%d'", movieId));
            return false;
        }

        String movieGenreDeleteQuery = new StringBuilder()
            .append("DELETE FROM MovieGenre")
            .append(" WHERE MovieId = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieGenreDeleteQuery)) {
            stmt.setLong(1, movieId);
            int affectedRows = stmt.executeUpdate();
            Log.debug(String.format("%d MovieGenre record(s) with the MovieId '%d' deleted", affectedRows, movieId));
            return affectedRows != 0;
        } catch (SQLException e) {
            Log.error(String.format("Deletion of MovieGenre record with the MovieId '%d' failed.", movieId));
            Log.error(e);
            throw e;
        }
    }

    public static void delete(List<MovieGenre> movieGenres) throws Exception {
        for(MovieGenre mg : movieGenres) {
            mg.delete();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

}
