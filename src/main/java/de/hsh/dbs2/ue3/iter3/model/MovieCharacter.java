package de.hsh.dbs2.ue3.iter3.model;

import de.hsh.dbs2.ue3.iter3.model.entity.StrongEntity;

public class MovieCharacter extends StrongEntity {
    private Long id;
    private Long movieId;
    private String character;
    private String alias;
    private Integer position;

    public MovieCharacter() {
    }

    public MovieCharacter(Long movieId, String character, String alias, Integer position) {
        this.movieId = movieId;
        this.character = character;
        this.alias = alias;
        this.position = position;
    }

    public MovieCharacter(Long id, Long movieId, String character, String alias, Integer position) {
        this.id = id;
        this.movieId = movieId;
        this.character = character;
        this.alias = alias;
        this.position = position;
    }

    protected String[] getDbFields() {
        return new String[]{"MovieId", "Character", "Alias", "Position"};
    }

    @Override
    protected String getTableName() {
        return "MovieCharacter";
    }

    @Override
    protected String getSequenceName() {
        return "moviechar_sequence";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, MovieId: %d, Character: %s, Alias: %s, Position: %d", id, movieId, character, alias, position);
    }
}
