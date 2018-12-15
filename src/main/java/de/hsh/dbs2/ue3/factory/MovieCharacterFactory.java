package de.hsh.dbs2.ue3.factory;

import de.hsh.dbs2.ue3.entity.MovieCharacter;
import de.hsh.dbs2.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class MovieCharacterFactory {

    private static MovieDbEntityManagerFactory movieDbEmFact = MovieDbEntityManagerFactory.getInstance();

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static MovieCharacter findByCharacter(String name) throws Exception {
        List<MovieCharacter> movChars = findByCharacter(name, false);
        return movChars.size() > 0 ? movChars.get(0) : null;
    }

    public static List<MovieCharacter> findByCharacter(String name, boolean globalSearch) throws Exception {
        name = name == null ? "" : name.trim();
        Log.debug(String.format("Looking for the MovieCharacters with the name '%s'", name));

        try {
            EntityManager em = movieDbEmFact.getEm();
            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
            CriteriaQuery<MovieCharacter> query = cBuilder.createQuery(MovieCharacter.class);

            Root<MovieCharacter> root = query.from(MovieCharacter.class);
            query.select(root);

            if(globalSearch) {
                query.where(cBuilder.like(cBuilder.upper(root.get("character")), "%" + name.toUpperCase() + "%"));
            } else {
                query.where(cBuilder.equal(cBuilder.upper(root.get("character")), name.toUpperCase()));
            }

            query.orderBy(cBuilder.asc(root.get("character")));

            Log.debug(String.format("MovieCharacter(s) found for the given substring '%s'!", name));
            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            Log.debug(String.format("No MovieCharacter found for the given name '%s'", name));
            return new ArrayList<>();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static void delete(MovieCharacter movChar) {
        Log.debug("Deleting MovieCharacter", movChar);

        try {
            movieDbEmFact.getEm().remove(movChar);
            Log.debug(String.format("MovieCharacter record with the Id '%d' deleted", movChar.getId()));
        } catch(Exception e) {
            Log.error(String.format("Deletion of MovieCharacter record with the Id '%d' failed.", movChar.getId()));
            throw e;
        }
    }
    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
