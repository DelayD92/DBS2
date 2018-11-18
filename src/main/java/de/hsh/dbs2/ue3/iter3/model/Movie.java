package de.hsh.dbs2.ue3.iter3.model;

import de.hsh.dbs2.ue3.iter3.model.entity.StrongEntity;

public class Movie extends StrongEntity {
    private Long id;
    private String title;
    private Integer year;
    private Character type;

    public Movie() {
    }

    public Movie(String title, Integer year, Character type) {
        this.title = title;
        this.year = year;
        this.type = type;
    }

    public Movie(Long movieId, String title, Integer year, Character type) {
        this.id = movieId;
        this.title = title;
        this.year = year;
        this.type = type;
    }

    protected String[] getDbFields() {
        return new String[]{"Title", "Year", "Type"};
    }

    @Override
    protected String getTableName() {
        return "Movie";
    }

    @Override
    protected String getSequenceName() {
        return "movie_sequence";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Character getType() {
        return type;
    }

    public void setType(Character type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Title: %s, Year: %d, Type: %c", id, title, year, type);
    }
}
