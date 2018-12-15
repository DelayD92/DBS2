package de.hsh.dbs2.ue3.factory;

import de.hsh.dbs2.ue3.entity.Genre;
import de.hsh.dbs2.ue3.entity.Movie;
import de.hsh.dbs2.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public final class GenreFactory {

    private static MovieDbEntityManagerFactory movieDbEmFact = MovieDbEntityManagerFactory.getInstance();

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static List<Genre> getAll() {
        try {
            EntityManager em = movieDbEmFact.getEm();
            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Genre> query = cBuilder.createQuery(Genre.class);

            Root<Genre> root = query.from(Genre.class);
            query.select(root);
            query.orderBy(cBuilder.asc(root.get("genre")));
            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static Genre findByGenre(String genreStr) throws Exception {
        try {
            Log.debug(String.format("Looking for a Genre with the genre '%s'", genreStr));
            CriteriaBuilder cBuilder = movieDbEmFact.getEm().getCriteriaBuilder();
            CriteriaQuery<Genre> query = cBuilder.createQuery(Genre.class);

            Root<Genre> root = query.from(Genre.class);
            query.select(root);
            query.where(cBuilder.equal(cBuilder.upper(root.get("genre")), genreStr.toUpperCase()));
            return movieDbEmFact.getEm().createQuery(query).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public static List<Genre> findAllByMovieId(Long movieId) throws Exception {
        try {
            Movie m = movieDbEmFact.getEm().find(Movie.class, movieId);
            return m == null ? null : new ArrayList<>(m.getGenres());
        } catch (NoResultException e) {
            return null;
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
