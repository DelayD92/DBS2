package de.hsh.dbs2.ue3.iter3;

import de.hsh.dbs2.ue3.iter3.factory.MovieFactory;
import de.hsh.dbs2.ue3.iter3.model.*;

import java.sql.SQLException;

public class TestFactory {

    private Person person;
    private Movie movie;
    private MovieCharacter movChar;
    private PersonPlays personPlays;
    private Genre genre;
    private MovieGenre movieGenre;

    public TestFactory() {
        person = new Person();
        movie = new Movie();
        movChar = new MovieCharacter();
        personPlays = new PersonPlays();
        genre = new Genre();
        movieGenre = new MovieGenre();
    }

    public void insert() throws SQLException {
        person.setName("Tester, Karl");
        person.setSex('M');
        person.insert();

        movie.setTitle("Die tolle Komoedie");
        movie.setYear(2012);
        movie.setType('C');
        movie.insert();

        movChar.setMovieId(movie.getId());
        movChar.setCharacter("Hauptrolle");
        movChar.setAlias(null);
        movChar.setPosition(1);
        movChar.insert();

        personPlays.setPersonId(person.getId());
        personPlays.setMovieCharId(movChar.getId());
        personPlays.insert();

        genre.setGenre("Unklar");
        genre.insert();

        movieGenre.setGenreId(genre.getId());
        movieGenre.setMovieId(movie.getId());
        movieGenre.insert();
    }

    public void read() throws Exception {
        MovieFactory.findById(movie.getId());
        MovieFactory.findByTitle("Die beste Komoedie");
    }

    public void update() throws SQLException {
        person.setName("Testerin, Karl");
        person.setSex('W');
        person.update();

        movie.setYear(2011);
        movie.setType('M');
        movie.setTitle("Die beste Komoedie");
        movie.update();

        movChar.setCharacter("Nebenrolle");
        movChar.setAlias("Karl einfach unverbesserlich");
        movChar.setPosition(2);
        movChar.update();

        personPlays.setPersonId(person.getId());
        personPlays.setMovieCharId(movChar.getId());
        personPlays.update();

        genre.setGenre("Klar");
        genre.update();

        movieGenre.setGenreId(genre.getId());
        movieGenre.setMovieId(movie.getId());
        movieGenre.update();
    }

    public void delete() throws SQLException {
        personPlays.delete();

        movieGenre.delete();

        movChar.delete();

        person.delete();

        movie.delete();

        genre.delete();
    }
}
