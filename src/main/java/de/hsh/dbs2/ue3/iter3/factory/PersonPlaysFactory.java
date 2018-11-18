package de.hsh.dbs2.ue3.iter3.factory;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.model.MovieCharacter;
import de.hsh.dbs2.ue3.iter3.model.Person;
import de.hsh.dbs2.ue3.iter3.model.PersonPlays;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonPlaysFactory {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static PersonPlays insert(Long personId, Long movieCharId) throws Exception {
        PersonPlays person = new PersonPlays(personId, movieCharId);
        person.insert();
        return person;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void findAndSetAllMovieCharsAndPersonsByMovieId(Long movieId, List<MovieCharacter> movieCharResults, List<Person> personResults) throws Exception {

        String movieCastQuery = new StringBuilder()
            .append("SELECT MC.Id MovieCharacterId, MC.MovieId, MC.Character, MC.Alias, MC.Position, P.Id PersonId, P.Name PersonName, P.Sex PersonSex")
            .append(" FROM MovieCharacter MC")
            .append(" JOIN PersonPlays PP ON PP.MovieCharId = MC.Id")
            .append(" JOIN Person P ON P.Id = PP.PersonId")
            .append(" WHERE MC.MovieId = ?")
            .append(" ORDER BY MC.Position")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieCastQuery)) {
            stmt.setLong(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return;
            }

            do {
                movieCharResults.add(new MovieCharacter(
                   rs.getLong("MovieCharacterId"),
                   rs.getLong("MovieId"),
                   rs.getString("Character"),
                   rs.getString("Alias"),
                   rs.getInt("Position")
                ));

                personResults.add(new Person(
                    rs.getLong("PersonId"),
                    rs.getString("PersonName"),
                    rs.getString("PersonSex").charAt(0)
                ));
            } while(rs.next());

        } catch (SQLException e) {
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static boolean deleteByMovieId(Long movieId) throws Exception {
        Log.debug(String.format("Deleting PersonPlays records for the MovieId '%d'", movieId));

        if (movieId == null || movieId <= 0) {
            Log.debug(String.format("Invalid MovieId '%d'", movieId));
            return false;
        }

        String movieCharDeleteQuery = new StringBuilder()
            .append("DELETE FROM PersonPlays PP")
            .append(" WHERE PP.MovieCharId IN (")
            .append("   SELECT MC.Id")
            .append("   FROM MovieCharacter MC")
            .append("   JOIN Movie M ON M.Id = MC.MovieId")
            .append("   WHERE MC.MovieId = ?")
            .append(" )")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieCharDeleteQuery)) {
            stmt.setLong(1, movieId);
            int affectedRows = stmt.executeUpdate();
            Log.debug(String.format("%d PersonPlays record(s) for the MovieId '%d' deleted", affectedRows, movieId));
            return affectedRows != 0;
        } catch (SQLException e) {
            Log.error(String.format("Deletion of PersonPlays record(s) for the MovieId '%d' failed.", movieId));
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
