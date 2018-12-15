package de.hsh.dbs2.ue3.factory;

import de.hsh.dbs2.ue3.entity.Movie;
import de.hsh.dbs2.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.*;

public final class MovieFactory {

    private static MovieDbEntityManagerFactory movieDbEmFact = MovieDbEntityManagerFactory.getInstance();

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static Movie insert(String title, int year, char type) throws Exception {
        return insert(new Movie(title, year, type));
    }

    public static Movie insert(Movie m) throws Exception {
        return (Movie) movieDbEmFact.persist(m);
    }
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static Movie findById(Long movieId) throws Exception {
        Log.debug(String.format("Looking for a movie with the Id '%d'", movieId));

        if (movieId == null || movieId <= 0) {
            Log.debug(String.format("Invalid Movie Id '%d'", movieId));
            return null;
        }

        try {
            Movie movie = movieDbEmFact.getEm().find(Movie.class, movieId);

            if(movie == null) {
                Log.debug(String.format("No movie found with the Id '%d'", movieId));
            } else {
                Log.debug(String.format("Movie record found with the Id '%d'", movieId), "Movie: ", movie);
            }
            return movie;
        } catch (Exception e) {
            Log.error(e);
            throw e;
        }
    }

    public static List<Movie> findByTitle(String title) throws Exception {
        return findByTitle(title, false);
    }

    public static List<Movie> findByTitle(String title, boolean globalSearch) throws Exception {
        List<Movie> results = new ArrayList<>();

        if(title == null || title.length() == 0) {
            Log.debug("Title is empty. Returning all records...");
            title = "";
            globalSearch = true;
        }

        Log.debug(String.format("Looking for Movie records with the title '%s'", title));

        EntityManager em = movieDbEmFact.getEm();
        CriteriaBuilder cBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Movie> query = cBuilder.createQuery(Movie.class);

        Root<Movie> root = query.from(Movie.class);
        query.select(root);

        title = title.toUpperCase();
        if(globalSearch) {
            query.where(cBuilder.like(cBuilder.upper(root.get("title")), "%" + title + "%"));
        } else {
            query.where(cBuilder.equal(cBuilder.upper(root.get("title")), title));
        }

        query.orderBy(cBuilder.asc(root.get("title")));

        try {
            results = em.createQuery(query).getResultList();
            Log.debug(String.format("%d movie record(s) found with the title '%s'", results.size(), title), results);
            return results;
        } catch (NoResultException e) {
            Log.debug(String.format("No movie found with the title '%s'", title));
            return results;
        }

    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static void deleteById(Long movieId) throws Exception {
        Log.debug(String.format("Deleting Movie record with the Id '%d'", movieId));

        try {
            movieDbEmFact.getEm().remove(movieDbEmFact.getEm().find(Movie.class, movieId));
            Log.debug(String.format("Movie record with the Id '%d' deleted", movieId));
        } catch(Exception e) {
            Log.error(String.format("Deletion of Movie record with the Id '%d' failed.", movieId));
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
