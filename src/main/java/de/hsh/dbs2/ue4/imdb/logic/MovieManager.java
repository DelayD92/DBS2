package de.hsh.dbs2.ue4.imdb.logic;

import de.hsh.dbs2.ue4.imdb.logic.dto.CharacterDTO;
import de.hsh.dbs2.ue4.imdb.logic.dto.MovieDTO;
import de.hsh.dbs2.ue3.entity.Genre;
import de.hsh.dbs2.ue3.entity.Movie;
import de.hsh.dbs2.ue3.entity.MovieCharacter;
import de.hsh.dbs2.ue3.entity.Person;
import de.hsh.dbs2.ue3.factory.*;
import de.hsh.dbs2.util.Log;

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
	    boolean isInsertOperation = movieDTO.getId() == null;

        try {
            MovieDbEntityManagerFactory.getInstance().startTransaction();
            Movie movie;

            if(isInsertOperation) {
                // 1. Insert Movie record
                Log.debug("movieDTO Id is null. Inserting new records...");
                movie = new Movie(movieDTO.getTitle(), movieDTO.getYear(), movieDTO.getType().charAt(0));
            } else {
                // 1. Get Movie record
                Log.debug("movieDTO has an Id. Updating records...");
                movie = MovieFactory.findById(movieDTO.getId());
                movie.setTitle(movieDTO.getTitle());
                movie.setYear(movieDTO.getYear());
                movie.setType(movieDTO.getType().charAt(0));
            }

            // 2. Add MovieGenre records
            movie.getGenres().clear();
            for(String genreStr : movieDTO.getGenres()) {
                Genre genre = GenreFactory.findByGenre(genreStr);
                if(genre != null) {
                    movie.addGenre(genre);
                }
            }

            // 3. Insert MovieCharacter and Person records
            int position = 0;

            movie.getCharacters().forEach(MovieCharacterFactory::delete);
            movie.getCharacters().clear();

            for(CharacterDTO movieDTOChar : movieDTO.getCharacters()) {
                MovieCharacter movieChar = new MovieCharacter(movieDTOChar.getCharacter(), movieDTOChar.getAlias(), ++position);
                Person p = PersonFactory.findByName(movieDTOChar.getPlayer());
                if(p == null) {
                    p = new Person(movieDTOChar.getPlayer(), 'M');
                }
                movieChar.setPerson(p);
                movie.addMovieChar(movieChar);
            }

            if(isInsertOperation) {
                MovieFactory.insert(movie);
                movieDTO.setId(movie.getId());
            } else {
                // no need to make a hibernate call
                // the commit will apply the updates... :))))))
            }

            MovieDbEntityManagerFactory.getInstance().commitTransaction();
        } catch (Exception e) {
            Log.error(e);
            Log.info(String.format("Error occurred during %s. Rolling back...", isInsertOperation ? "insert" : "update"));
            MovieDbEntityManagerFactory.getInstance().rollbackTransaction();
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
        try {
            MovieDbEntityManagerFactory.getInstance().startTransaction();
            return MovieFactory.findByTitle(search, true)
                .stream()
                .map(MovieManager::constructMovieDTOFromMovie)
                .collect(Collectors.toList());
        } finally {
            MovieDbEntityManagerFactory.getInstance().commitTransaction();
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
        try {
            MovieDbEntityManagerFactory.getInstance().startTransaction();
            MovieFactory.deleteById(movieId);
        } finally {
            MovieDbEntityManagerFactory.getInstance().commitTransaction();
        }
	}

	public MovieDTO getMovie(long movieId) throws Exception {
        try {
            MovieDbEntityManagerFactory.getInstance().startTransaction();
            Movie movie = MovieFactory.findById(movieId);
            if(movie == null) return null;
            return MovieManager.constructMovieDTOFromMovie(movie);
        } catch (Exception e){
            Log.error(e);
            return null;
        } finally {
            MovieDbEntityManagerFactory.getInstance().commitTransaction();
        }
	}

	// ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static MovieDTO constructMovieDTOFromMovie(Movie movie) {
        try {
            Log.debug("Construction MovieDTO from movie [", movie, "]");
            MovieDTO movieDTO = new MovieDTO();
            movieDTO.setId(movie.getId());
            movieDTO.setTitle(movie.getTitle());
            movieDTO.setType(String.valueOf(movie.getType()));
            movieDTO.setYear(movie.getYear());
            movieDTO.setGenres(movie.getGenres().stream().map(Genre::getGenre).collect(Collectors.toSet()));

            movieDTO.getCharacters().clear();
            movie.getCharacters().forEach(movChar -> {
                CharacterDTO charDTO = new CharacterDTO();
                charDTO.setCharacter(movChar.getCharacter());
                charDTO.setAlias(movChar.getAlias());
                charDTO.setPlayer(movChar.getPerson().getName());
                movieDTO.getCharacters().add(charDTO);
            });
            return movieDTO;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
