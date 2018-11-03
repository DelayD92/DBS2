package de.hsh.dbs2.ue3;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import de.hsh.dbs2.ue3.core.Config;
import de.hsh.dbs2.ue3.data.Database;
import de.hsh.dbs2.ue3.utils.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReadMovieDB {

    private static final String CONF_FILE_PATH = Paths.get("/", "main", "resources", "options", "settings.json").toString();

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
        Config config = loadConfigs(args);
        Log.setLogLevel(config.logLevel);
        Database.init(config.db);
    }

    private static Config loadConfigs(String... args) throws FileNotFoundException {
        try {
            InputStream is = ClassLoader.class.getResourceAsStream(CONF_FILE_PATH);
            if (is == null) {
                throw new FileNotFoundException();
            }
            JsonReader reader = new JsonReader(new InputStreamReader(is));
            return new Gson().fromJson(reader, Config.class);
        } catch (Exception e) {
            System.out.printf("Error loading config file %s. Please make sure it exists.\n", CONF_FILE_PATH);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void run(String... args) {
        List<Integer> movieIdList;

        if (args == null || args.length == 0) {
            movieIdList = getAllMovieIds();
        } else {
            movieIdList = Arrays.stream(args).filter(val -> {
                try {
                    Integer.valueOf(val);
                    return true;
                } catch (Exception e) {
                    Log.warn(String.format("'%s' is not an integer! Skipping...", val));
                    return false;
                }
            })
                .map(Integer::valueOf)
                .distinct()
                .collect(Collectors.toList());
        }

        if (movieIdList == null || movieIdList.size() == 0) {
            Log.warn("No movie id given");
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
            .append("SELECT MovieID FROM Movie")
            .toString();

        List<Integer> movieIdList = new ArrayList<>();

        try (Statement stmt = Database.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(movieQuery);

            while (rs.next()) {
                movieIdList.add(rs.getInt("MovieID"));
            }

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
            .append(" WHERE MovieID = ?")
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
            .append(" JOIN MovieGenre MG ON MG.Genre = G.GenreID")
            .append(" WHERE MG.Movie = ?")
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
            .append(" JOIN PersonPlays PP ON PP.MovieChar = MC.MovCharID")
            .append(" JOIN Person P ON P.PersonID = PP.Person")
            .append(" WHERE MC.Movie = ?")
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
