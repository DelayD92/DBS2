package de.hsh.dbs2.ue3.iter3.model;

import de.hsh.dbs2.ue3.iter3.model.entity.StrongEntity;

public class Genre extends StrongEntity {
    private Long id;
    private String genre;

    public Genre() {
    }

    public Genre(Long Id, String genre) {
        this.id = Id;
        this.genre = genre;
    }

    public Genre(String genre) {
        this.genre = genre;
    }

    protected String[] getDbFields() {
        return new String[]{"Genre"};
    }

    @Override
    protected String getTableName() {
        return "Genre";
    }

    @Override
    protected String getSequenceName() {
        return "genre_sequence";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Genre: %s", id, genre);
    }
}
