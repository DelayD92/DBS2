package de.hsh.dbs2.imdb.gui;

import de.hsh.dbs2.imdb.logic.GenreManager;
import de.hsh.dbs2.imdb.logic.MovieManager;
import de.hsh.dbs2.imdb.logic.dto.MovieDTO;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieDialogCallback {
	
	private MovieManager mm = new MovieManager();
	private GenreManager gm = new GenreManager();
	
	public List<MovieDTO> runSearch(String search) {
		List<MovieDTO> movies;
		try {
			movies = mm.getMovieList(search);
		} catch (Exception e) {
			new ShowErrorDialog("Fehler beim der Suche", e);
			movies = new ArrayList<MovieDTO>();
		}
		return movies;
	}

	public void addMovie() {
		movieDialog(null);
	}

	public void editMovie(long movieId) {
		movieDialog(movieId);
	}

	public void deleteMovie(long movieId) {
		try {
			mm.deleteMovie(movieId);
		} catch (Exception e) {
			new ShowErrorDialog("Fehler beim Löschen des Films", e);
		}
	}

	private void movieDialog(Long movieId) {

		MovieDTO movie;
		if (movieId == null)
			movie = new MovieDTO();
		else {
			try {
				movie = mm.getMovie(movieId);
			} catch (Exception e) {
				new ShowErrorDialog("Fehler beim Laden des Films", e);
				return;
			}
		}
		
		List<String> allGenres;
		try {
			allGenres = gm.getGenres();
		} catch (Exception e) {
			new ShowErrorDialog("Fehler beim Laden der Genres", e);
			return;
		}

		MovieDialog movieDialog = new MovieDialog(new MovieDialogCallback(), allGenres);
		movieDialog.setMovie(movie);
		
		boolean finished = false;
		while (!finished) {
			movieDialog.setVisible(true);
			finished = true;

			if (movieDialog.getOutcome().equals("ok")) {
				try {
					mm.insertUpdateMovie(movie);
				} catch (Exception e) {
					new ShowErrorDialog("Fehler beim Speichern des Films", e);
					finished = false;  // Movie Dialog offen halten
				}
			}
		}
	}
}
