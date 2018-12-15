package de.hsh.dbs2.ue4;

import de.hsh.dbs2.ue3.entity.Genre;
import de.hsh.dbs2.ue3.entity.Movie;
import de.hsh.dbs2.ue3.entity.MovieCharacter;
import de.hsh.dbs2.ue3.entity.Person;
import de.hsh.dbs2.ue3.factory.GenreFactory;
import de.hsh.dbs2.ue3.factory.MovieDbEntityManagerFactory;
import de.hsh.dbs2.ue3.factory.MovieFactory;
import de.hsh.dbs2.util.Log;

public class Test {
    private static MovieDbEntityManagerFactory movieDbEmFact;

    private Movie movie;
    private Genre genre;
    private MovieCharacter movChar;
    private Person person;

    Test() {
        movie = new Movie();
        genre = new Genre();
        movChar = new MovieCharacter();
        person = new Person();
    }

    private void insert()  {
        movie.setTitle("Die tolle Komoedie");
        movie.setYear(2012);
        movie.setType('C');

        genre.setGenre("Unklar");

        person.setName("Tester, Karl");
        person.setSex('M');

        movChar.setCharacter("Hauptrolle");
        movChar.setAlias(null);
        movChar.setPosition(1);

        movChar.setPerson(person);
        movie.addGenre(genre);
        movie.addMovieChar(movChar);

        movieDbEmFact.persist(movie);
    }

    private void read() throws Exception {
        MovieFactory.findById(movie.getId());
        MovieFactory.findByTitle("dIe beSte kOmoedie");
        MovieFactory.findByTitle("koMoEdie", true);
        MovieFactory.findByTitle("", true);

        GenreFactory.getAll();
        GenreFactory.findAllByMovieId(movie.getId());
        GenreFactory.findAllByMovieId(3L);
        GenreFactory.findByGenre("test");
    }

    private void update() throws Exception   {
        person.setName("Testerin, Karl");
        person.setSex('W');

        movie.setYear(2011);
        movie.setType('M');
        movie.setTitle("Die beste Komoedie");

        movChar.setCharacter("Nebenrolle");
        movChar.setAlias("Karl einfach unverbesserlich");
        movChar.setPosition(2);

        genre.setGenre("Klar");
    }

    public void delete() throws Exception {
        MovieFactory.deleteById(movie.getId());
    }

    public static void main(String... args) {
        Log.setLogLevel(Log.LogLevel.DEBUG);

        movieDbEmFact = MovieDbEntityManagerFactory.getInstance();
        Test test = new Test();

        try {
            movieDbEmFact.startTransaction();
            test.insert();
            test.read();
            test.update();
            test.read();
            test.delete();
            movieDbEmFact.commitTransaction();
        } catch (Exception e) {
            movieDbEmFact.rollbackTransaction();
            e.printStackTrace();
        } finally {
            movieDbEmFact.close();
        }
    }
}
