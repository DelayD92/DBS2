package de.hsh.dbs2.imdb.logic;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.imdb.logic.dto.CharacterDTO;
import de.hsh.dbs2.imdb.logic.dto.MovieDTO;
import de.hsh.dbs2.ue3.iter3.factory.*;
import de.hsh.dbs2.ue3.iter3.model.Genre;
import de.hsh.dbs2.ue3.iter3.model.Movie;
import de.hsh.dbs2.ue3.iter3.model.MovieCharacter;
import de.hsh.dbs2.ue3.iter3.model.Person;
import de.hsh.dbs2.util.Log;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class MovieManager {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Speichert die uebergebene Version des Films neu in der Datenbank oder aktualisiert den
	 * existierenden Film.
	 * Dazu werden die Daten des Films selbst (Titel, Jahr, Typ) beruecksichtigt,
	 * aber auch alle Genres, die dem Film zugeordnet sind und die Liste der Charaktere
	 * auf den neuen Stand gebracht.
	 * @param movieDTO Film-Objekt mit Genres und Charakteren.
	 * @throws Exception
	 */
	public void insertUpdateMovie(MovieDTO movieDTO) throws Exception {

        Database db = Database.getInstance();
        try {
            db.startTransaction();

            if(movieDTO.getId() == null) {
                Log.debug("movieDTO Id is null. Inserting new records...");
                insertMovie(movieDTO);
            } else {
                Log.debug("movieDTO has an Id. Updating records...");
                updateMovie(movieDTO);
            }

            db.commitTransaction();
        } catch (Exception e) {
            Log.error(e);

            try {
                Log.info("Error occurred during insert/update. Rolling back...");
                db.rollbackTransaction();
            } catch (SQLException e2) {
                Log.error(e2);
            }
        }
	}

    private void insertMovie(MovieDTO movieDTO) throws Exception {
        // 1. Insert Movie record
        Movie movie = MovieFactory.insert(movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType().charAt(0));
        movieDTO.setId(movie.getId());

        // 2. Insert MovieGenre records
        for(String genreStr : movieDTO.getGenres()) {
            Genre genre = GenreFactory.findByGenre(genreStr);
            MovieGenreFactory.insert(movie.getId(), genre.getId());
        }

        // 3. Insert MovieCharacter records
        int position = 0;
        for(CharacterDTO movieDTOChar : movieDTO.getCharacters()) {
            MovieCharacter movieCharacter = MovieCharacterFactory.insert(movie.getId(), movieDTOChar.getCharacter(), movieDTOChar.getAlias(), ++position);

            // 4. Insert PersonPlays record
            List<Person> person = PersonFactory.findByName(movieDTOChar.getPlayer());
            PersonPlaysFactory.insert(person.get(0).getId(), movieCharacter.getId());
        }
    }

    private void updateMovie(MovieDTO movieDTO) throws Exception {
        // 1. Update Movie
        Movie movie = MovieFactory.findById(movieDTO.getId());
        if(!MovieFactory.update(movie, movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType().charAt(0))) {
            throw new Exception("Movie update failed: " + movie.toString());
        }

        // 2.1 Delete old MovieGenres records
        MovieGenreFactory.deleteByMovieId(movieDTO.getId());

        // 2.2 Insert new MovieGenres records
        for(String genreStr : movieDTO.getGenres()) {
            Genre genre = GenreFactory.findByGenre(genreStr);
            MovieGenreFactory.insert(movie.getId(), genre.getId());
        }

        // 3.1 Delete old PersonPlays records
        PersonPlaysFactory.deleteByMovieId(movieDTO.getId());
        // 3.2 Delete old MovieCharacter records
        MovieCharacterFactory.deleteByMovieId(movieDTO.getId());

        // 3.3 Delete old MovieCharacter records
        int position = 0;
        for(CharacterDTO movieDTOChar : movieDTO.getCharacters()) {
            MovieCharacter movieCharacter = MovieCharacterFactory.insert(movie.getId(), movieDTOChar.getCharacter(), movieDTOChar.getAlias(), ++position);

            // 3.4. Insert PersonPlays record
            List<Person> person = PersonFactory.findByName(movieDTOChar.getPlayer());
            PersonPlaysFactory.insert(person.get(0).getId(), movieCharacter.getId());
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    /**
     * Ermittelt alle Filme, deren Filmtitel den Suchstring enthaelt.
     * Wenn der String leer ist, sollen alle Filme zurueckgegeben werden.
     * Der Suchstring soll ohne Ruecksicht auf Gross/Kleinschreibung verarbeitet werden.
     * @param search Suchstring.
     * @return Liste aller passenden Filme als MovieDTO
     * @throws Exception
     */
    public List<MovieDTO> getMovieList(String search) throws Exception {
        search = search == null ? "" : search.trim();

        Database db = Database.getInstance();

        try {
            db.startTransaction();
            return MovieFactory.findByTitle(search, true)
                .stream()
                .map(MovieManager::constructMovieDTOFromMovie)
                .collect(Collectors.toList());
        } finally {
            db.commitTransaction();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

	/**
	 * Loescht einen Film aus der Datenbank. Es werden auch alle abhaengigen Objekte geloescht,
	 * d.h. alle Charaktere und alle Genre-Zuordnungen.
	 * @param movieId
	 * @throws Exception
	 */
	public void deleteMovie(Long movieId) throws Exception {
        if(movieId == null) {
            Log.debug("Given movieId is null. Returning...");
            return;
        }

        Database db = Database.getInstance();

        try {
            db.startTransaction();

            MovieGenreFactory.deleteByMovieId(movieId);
            PersonPlaysFactory.deleteByMovieId(movieId);
            MovieCharacterFactory.deleteByMovieId(movieId);
            MovieFactory.deleteById(movieId);

            db.commitTransaction();
        } catch (Exception e) {
            Log.error(e);

            try {
                Log.info("Error occurred during deletion. Rolling back...");
                db.rollbackTransaction();
            } catch (SQLException e2) {
                Log.error(e2);
            }
        }
	}

	public MovieDTO getMovie(long movieId) throws Exception {
       Movie movie = MovieFactory.findById(movieId);
       if(movie == null) return null;
       return MovieManager.constructMovieDTOFromMovie(movie);
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static MovieDTO constructMovieDTOFromMovie(Movie movie) {
        try {
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setType(String.valueOf(movie.getType()));
            movieDTO.setYear(movie.getYear());

            GenreManager.findMovieGenresByMovieId(movieDTO.getId()).forEach(movieDTO::addGenre);
            MovieCharacterManager.findMovieCastByMovieId(movieDTO.getId()).forEach(movieDTO::addCharacter);
            return movieDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
