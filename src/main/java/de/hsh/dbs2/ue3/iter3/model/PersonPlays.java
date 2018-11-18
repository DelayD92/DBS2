package de.hsh.dbs2.ue3.iter3.model;

import de.hsh.dbs2.ue3.iter3.model.entity.WeakEntity;

public class PersonPlays extends WeakEntity {
    private Long personId;
    private Long movieCharId;

    public PersonPlays() {
    }

    public PersonPlays(Long personId, Long movieCharId) {
        this.personId = personId;
        this.movieCharId = movieCharId;
    }

    @Override
    protected String[] getDbFields() {
        return new String[]{"PersonId", "MovieCharId"};
    }

    protected String getTableName() {
        return "PersonPlays";
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getMovieCharId() {
        return movieCharId;
    }

    public void setMovieCharId(Long movieCharId) {
        this.movieCharId = movieCharId;
    }

    @Override
    public String toString() {
        return String.format("PersonId: %d, MovieId: %d", personId, movieCharId);
    }
}
