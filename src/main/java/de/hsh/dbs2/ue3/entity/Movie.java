package de.hsh.dbs2.ue3.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OrderBy;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Entity
@SelectBeforeUpdate
@DynamicUpdate
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable=false)
    private String title;

    @Column(nullable=false, precision = 4)
    private int year;

    @Column(nullable=false)
    private char type;

    @ManyToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Genre.class)
    @JoinTable(name="MovieGenre",
        joinColumns = @JoinColumn(name = "MovieId"),
        inverseJoinColumns = @JoinColumn(name = "GenreId")
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy="movie", cascade = CascadeType.ALL, fetch=FetchType.LAZY, targetEntity = MovieCharacter.class)
    @OrderBy("position ASC, character ASC")
    private Set<MovieCharacter> characters = new HashSet<>();

    public Movie() {}

    public Movie(String title, int year, char type) {
        this.title = title;
        this.year = year;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public char getType() {
        return type;
    }

    public void setType(char type) {
        this.type = type;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> movieGenre) {
        this.genres = movieGenre;
    }

    public void addGenre(Genre ...genres) {
        this.genres.addAll(Arrays.asList(genres));
    }

    public Set<MovieCharacter> getCharacters() {
        return characters;
    }

    public void setCharacters(Set<MovieCharacter> movieCharacters) {
        this.characters = movieCharacters;
    }

    public void addMovieChar(MovieCharacter ...movieChars) {
        for(MovieCharacter movChar : movieChars) {
            this.characters.add(movChar);
            movChar.setMovie(this);
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Title: %s, Year: %d, Type: %c", id, title, year, type);
    }
}
