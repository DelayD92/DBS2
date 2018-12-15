package de.hsh.dbs2.ue3.factory;

import de.hsh.dbs2.ue3.entity.Person;
import de.hsh.dbs2.util.Log;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public class PersonFactory {

    private static MovieDbEntityManagerFactory movieDbEmFact = MovieDbEntityManagerFactory.getInstance();

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
    public static Person findByName(String name) throws Exception {
        List<Person> persons = findByName(name, false);
        return persons.size() > 0 ? persons.get(0) : null;
    }

    public static List<Person> findByName(String name, boolean globalSearch) throws Exception {
        name = name == null ? "" : name.trim();
        Log.debug(String.format("Looking for the persons with the name '%s'", name));

        try {
            EntityManager em = movieDbEmFact.getEm();
            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
            CriteriaQuery<Person> query = cBuilder.createQuery(Person.class);

            Root<Person> root = query.from(Person.class);
            query.select(root);

            if(globalSearch) {
                query.where(cBuilder.like(cBuilder.upper(root.get("name")), "%" + name.toUpperCase() + "%"));
            } else {
                query.where(cBuilder.equal(cBuilder.upper(root.get("name")), name.toUpperCase()));
            }

            query.orderBy(cBuilder.asc(root.get("name")));

            Log.debug(String.format("Person(s) found for the given substring '%s'!", name));
            return em.createQuery(query).getResultList();
        } catch (NoResultException e) {
            Log.debug(String.format("No person found for the given name '%s'", name));
            return new ArrayList<>();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
