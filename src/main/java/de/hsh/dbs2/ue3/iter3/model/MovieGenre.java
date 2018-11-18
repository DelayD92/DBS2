package de.hsh.dbs2.ue3.iter3.model;

import de.hsh.dbs2.ue3.iter3.model.entity.WeakEntity;

public class MovieGenre extends WeakEntity {
    private Long movieId;
    private Long genreId;

    public MovieGenre() {
    }

    public MovieGenre(Long movieId, Long genreId) {
        this.movieId = movieId;
        this.genreId = genreId;
    }

    protected String[] getDbFields() {
        return new String[]{"MovieId", "GenreId"};
    }

    protected String getTableName() {
        return "MovieGenre";
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public Long getGenreId() {
        return genreId;
    }

    public void setGenreId(Long genreId) {
        this.genreId = genreId;
    }

    @Override
    public String toString() {
        return String.format("MovieId: %d, GenreId: %d", movieId, genreId);
    }
}
