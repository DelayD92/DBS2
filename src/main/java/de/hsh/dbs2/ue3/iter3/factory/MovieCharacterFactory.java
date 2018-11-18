package de.hsh.dbs2.ue3.iter3.factory;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.model.MovieCharacter;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MovieCharacterFactory {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static MovieCharacter insert(Long movieId, String character, String alias, int position) throws Exception {
        MovieCharacter movieChar = new MovieCharacter(movieId, character, alias, position);
        movieChar.insert();
        return movieChar;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static List<MovieCharacter> findAllByMovieId(Long movieId) throws Exception {
        Log.debug(String.format("Looking for MovieCharacters with the MovieId '%d'", movieId));

        String movieQuery = new StringBuilder()
            .append("SELECT Id, MovieId, Character, Alias, Position")
            .append(" FROM MovieCharacter")
            .append(" WHERE MovieId = ?")
            .toString();

        List<MovieCharacter> results = new ArrayList<>();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setLong(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                Log.debug(String.format("No MovieCharacter found with the MovieId '%d'", movieId));
                return results;
            }

            do {
                results.add(new MovieCharacter(
                    rs.getLong("Id"),
                    rs.getLong("MovieId"),
                    rs.getString("Character"),
                    rs.getString("Alias"),
                    rs.getInt("Position")
                ));

            } while(rs.next());

            Log.debug(String.format("%d MovieCharacter record(s) found with the MovieId '%d'", results.size(), movieId));

            return results;
        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean deleteByMovieId(Long movieId) throws Exception {
        Log.debug(String.format("Deleting MovieCharacter record(s) with the MovieId '%d'", movieId));

        if (movieId == null || movieId <= 0) {
            Log.debug(String.format("Invalid MovieId '%d'", movieId));
            return false;
        }

        String movieCharDeleteQuery = new StringBuilder()
            .append("DELETE FROM MovieCharacter")
            .append(" WHERE MovieId = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieCharDeleteQuery)) {
            stmt.setLong(1, movieId);
            int affectedRows = stmt.executeUpdate();
            Log.debug(String.format("%d MovieCharacter record(s) with the MovieId '%d' deleted", affectedRows, movieId));
            return affectedRows != 0;
        } catch (SQLException e) {
            Log.error(String.format("Deletion of MovieCharacter record(s) with the MovieId '%d' failed.", movieId));
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
