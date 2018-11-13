package de.hsh.dbs2.ue3.iter2;

import de.hsh.dbs2.core.Config;
import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.utils.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadMovieDB {

    public static void main(String... args) {
        try {
            bootstrap();
            run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void bootstrap(String... args) throws Exception {
        Config config = Config.Loader.loadConfigs();
        Log.setLogLevel(config.logLevel);
        Database.init(config.db);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void run(String... args) {
        List<Integer> movieIdList;

        if (args == null || args.length == 0) {
            movieIdList = getAllMovieIds();
        } else {
            movieIdList = Arrays.stream(args).filter(val -> {
                try {
                    if (Integer.valueOf(val) > 0) {
                        return true;
                    }
                    throw new NumberFormatException();
                } catch (Exception e) {
                    Log.warn(String.format("'%s' is not a valid ID! Skipping...", val));
                    return false;
                }
            }).map(Integer::valueOf).distinct().collect(Collectors.toList());
        }

        if (movieIdList == null || movieIdList.size() == 0) {
            Log.warn("No movie id given. Returning...");
            return;
        }

        int counter = 0;
        for (int id : movieIdList) {
            System.out.printf("%d.\n", ++counter);
            printMovieInfo(id);
            System.out.println();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void printMovieInfo(int movieId) {
        Log.debug("Looking for the movie with the ID: ", movieId);
        String movie = findMovieById(movieId);

        if (movie == null) {
            System.out.printf("No movie found with the given ID %s \n", movieId);
            return;
        }

        String genre = findGenresOfMovieByMovieId(movieId);
        List<String> cast = findMovieCastByMovieId(movieId);

        System.out.printf("Kinofilm: %s\n", movie);
        System.out.printf("Genre: %s\n", genre != null ? genre : "N/A");
        System.out.printf("Darsteller: \n %s\n", cast != null ? String.join(" \n ", cast) : "N/A");
    }

    private static List<Integer> getAllMovieIds() {
        String movieQuery = new StringBuilder()
            .append("SELECT ID FROM Movie")
            .toString();

        try (Statement stmt = Database.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(movieQuery);

            if (!rs.next()) {
                return null;
            }

            List<Integer> movieIdList = new ArrayList<>();

            do {
                movieIdList.add(rs.getInt("ID"));
            } while (rs.next());

            return movieIdList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String findMovieById(int movieId) {
        String movieQuery = new StringBuilder()
            .append("SELECT Title, Year")
            .append(" FROM Movie")
            .append(" WHERE Id = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return String.format("%s (%d)",
                rs.getString("Title"),
                rs.getInt("Year"));

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String findGenresOfMovieByMovieId(int movieId) {
        String genresQuery = new StringBuilder()
            .append("SELECT LISTAGG(G.Genre, ' | ') WITHIN GROUP (ORDER BY G.Genre) \"Genre\"")
            .append(" FROM Genre G")
            .append(" JOIN MovieGenre MG ON MG.GenreId = G.Id")
            .append(" WHERE MG.MovieId = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(genresQuery)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            return String.format("%s", rs.getString("Genre"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static List<String> findMovieCastByMovieId(int movieId) {
        String movieCastQuery = new StringBuilder()
            .append("SELECT MC.Character, P.Name")
            .append(" FROM MovieCharacter MC")
            .append(" JOIN PersonPlays PP ON PP.MovieCharId = MC.Id")
            .append(" JOIN Person P ON P.Id = PP.PersonId")
            .append(" WHERE MC.MovieId = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieCastQuery)) {
            stmt.setInt(1, movieId);
            ResultSet rs = stmt.executeQuery();

            if (!rs.next()) {
                return null;
            }

            List<String> cast = new ArrayList<>();

            do {
                cast.add(
                    String.format("%s: %s",
                        rs.getString("Character"),
                        rs.getString("Name")
                    )
                );
            } while (rs.next());

            return cast;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
