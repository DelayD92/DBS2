package de.hsh.dbs2.ue3.iter2;

import de.hsh.dbs2.core.Config;
import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.factory.MovieFactory;
import de.hsh.dbs2.ue3.iter3.model.Movie;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReadMovieDB {

    public static void main(String... args) {
        try {
            bootstrap();
            //runIter2(args);
            runIter3(args);
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

    private static void runIter2(String... args) {
        List<Long> movieIdList = null;

        if (args == null || args.length == 0) {
            Log.info("No argument given. Looking up for all movies in database...");
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
            }).map(Long::valueOf).distinct().collect(Collectors.toList());
        }

        if (movieIdList == null || movieIdList.size() == 0) {
            Log.warn("No movie id given or found. Returning...");
            return;
        }

        int counter = 0;
        for (Long id : movieIdList) {
            System.out.printf("%d.\n", ++counter);
            processMovieIdIter2(id);
            System.out.println();
        }
    }

    private static void runIter3(String... args) {
        List<Object> movieArgList = null;

        if (args == null || args.length == 0) {
            Log.info("No argument given. Looking up for all movies in database...");
            List<Long> movieIdList = getAllMovieIds();
            if(movieIdList != null) {
                movieArgList = new ArrayList<>(movieIdList);
            }
        } else {
            movieArgList = Arrays.stream(args).distinct()
                .map(val -> {
                    if(val.startsWith("'") && val.endsWith("'")) {
                        val = val.substring(1, val.length() - 1);
                        return val.length() > 0 ? (Object) val : null;
                    }

                    try {
                        Long id = Long.valueOf(val);
                        if (id > 0) {
                            return id;
                        }
                        throw new NumberFormatException();
                    } catch (Exception e) {
                        Log.warn(String.format("'%s' is not a valid ID! Skipping...", val));
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        }

        if (movieArgList == null || movieArgList.size() == 0) {
            Log.warn("No movie id/title given or found. Returning...");
            return;
        }

        Database db = Database.getInstance();
        try {
            db.startTransaction();

            int counter = 0;
            for (Object arg : movieArgList) {
                System.out.printf("%d.\n", ++counter);

                if(arg.getClass() == Long.class) {
                    processMovieIdIter3((Long) arg);
                } else if(arg.getClass() == String.class) {
                    processMovieTitleIter3(arg.toString());
                }

                System.out.println();
            }

        } catch (Exception e) {
            Log.error(e);
        } finally {
            try {
                db.commitTransaction();
            } catch (SQLException e) {
                Log.error(e);
            }
        }
    }

    private static void processMovieIdIter2(Long movieId) {
        Log.debug("Looking for the movie with the ID: ", movieId);
        String movie = findMovieById(movieId);

        if (movie == null) {
            System.out.printf("No movie found with the ID %s \n", movieId);
            return;
        }

        String genre = findMovieGenresByMovieId(movieId);
        List<String> cast = findMovieCastByMovieId(movieId);

        printMovieInfo(movie, genre, cast);
    }

    private static void processMovieIdIter3(Long movieId) throws Exception {
        Movie movie = MovieFactory.findById(movieId);

        if (movie == null) {
            return;
        }

        String movieInfo = String.format("%s (%d)", movie.getTitle(), movie.getYear());
        String genre = findMovieGenresByMovieId(movieId);
        List<String> cast = findMovieCastByMovieId(movieId);

        printMovieInfo(movieInfo, genre, cast);

    }

    private static void processMovieTitleIter3(String title) throws Exception {
        List<Movie> movies = MovieFactory.findByTitle(title);

        if (movies == null) {
            System.out.printf("No movie found with the title '%s' \n", title);
            return;
        }

        movies.forEach(movie -> {
            String movieInfo = String.format("%s (%d)", movie.getTitle(), movie.getYear());
            String genre = findMovieGenresByMovieId(movie.getId());
            List<String> cast = findMovieCastByMovieId(movie.getId());
            printMovieInfo(movieInfo, genre, cast);
        });
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void printMovieInfo(String movie, String genre, List<String> cast) {
        System.out.printf("Kinofilm: %s\n", movie);
        System.out.printf("Genre: %s\n", genre != null ? genre : "N/A");
        System.out.printf("Darsteller: \n %s\n", cast != null ? String.join(" \n ", cast) : "N/A");
    }

    private static List<Long> getAllMovieIds() {
        String movieQuery = new StringBuilder()
            .append("SELECT ID FROM Movie")
            .toString();

        try (Statement stmt = Database.getInstance().createStatement()) {
            ResultSet rs = stmt.executeQuery(movieQuery);

            if (!rs.next()) {
                return null;
            }

            List<Long> movieIdList = new ArrayList<>();

            do {
                movieIdList.add(rs.getLong("ID"));
            } while (rs.next());

            return movieIdList;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String findMovieById(Long movieId) {
        String movieQuery = new StringBuilder()
            .append("SELECT Title, Year")
            .append(" FROM Movie")
            .append(" WHERE Id = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
            stmt.setLong(1, movieId);
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

    private static String findMovieGenresByMovieId(Long movieId) {
        String genresQuery = new StringBuilder()
            .append("SELECT LISTAGG(G.Genre, ' | ') WITHIN GROUP (ORDER BY G.Genre) \"Genre\"")
            .append(" FROM Genre G")
            .append(" JOIN MovieGenre MG ON MG.GenreId = G.Id")
            .append(" WHERE MG.MovieId = ?")
            .toString();

        try (PreparedStatement stmt = Database.getInstance().prepareStatement(genresQuery)) {
            stmt.setLong(1, movieId);
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

    private static List<String> findMovieCastByMovieId(Long movieId) {
        String movieCastQuery = new StringBuilder()
            .append("SELECT MC.Character, P.Name")
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
