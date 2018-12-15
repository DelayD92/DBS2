package de.hsh.dbs2.ue3.entity;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;

@Entity
@SelectBeforeUpdate
@DynamicUpdate
public class MovieCharacter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    private String character;

    private String alias;

    private int position;

    @ManyToOne(fetch=FetchType.LAZY, targetEntity = Movie.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="Movie", nullable=false, foreignKey = @ForeignKey(name="fk_moviechar_movie"))
    private Movie movie;

    @ManyToOne(fetch=FetchType.LAZY, targetEntity = Person.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name="Person", nullable=false, foreignKey = @ForeignKey(name="fk_moviechar_person"))
    private Person person;

    public MovieCharacter() {}

    public MovieCharacter(String character, String alias, int position) {
        this.character = character;
        this.alias = alias;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
        if(person != null) {
            person.addRole(this);
        }
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Movie: [%s], Person: [%s], Character: %s, Alias: %s, Position: %d", id, movie, person, character, alias, position);
    }
}
