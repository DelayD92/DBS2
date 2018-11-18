package de.hsh.dbs2.imdb.logic;

import de.hsh.dbs2.ue3.iter3.factory.GenreFactory;
import de.hsh.dbs2.ue3.iter3.model.Genre;

import java.util.List;
import java.util.stream.Collectors;

public class GenreManager {

	/**
	 * Ermittelt eine vollstaendige Liste aller in der Datenbank abgelegten Genres
	 * Die Genres werden alphabetisch sortiert zurueckgeliefert.
	 * @return Alle Genre-Namen als String-Liste
	 * @throws Exception
	 */
	public List<String> getGenres() throws Exception {
        return GenreFactory.getAll().stream().map(Genre::getGenre).collect(Collectors.toList());
	}

    public static List<String> findMovieGenresByMovieId(Long movieId) throws Exception {
        return GenreFactory.findAllByMovieId(movieId).stream().map(Genre::getGenre).collect(Collectors.toList());
    }

}
