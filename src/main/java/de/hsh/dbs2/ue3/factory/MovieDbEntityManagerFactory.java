package de.hsh.dbs2.ue3.factory;

import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

public final class MovieDbEntityManagerFactory {

    private static MovieDbEntityManagerFactory instance = null;

    private final EntityManagerFactory emf;
    private final EntityManager em;

    private MovieDbEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("moviedb");
        em = emf.createEntityManager();
    }

    public static MovieDbEntityManagerFactory getInstance() {
        if(instance == null) {
            instance = new MovieDbEntityManagerFactory();
        }
        return instance;
    }

    public EntityManager getEm() {
        return this.em;
    }

    public void startTransaction() {
        em.getTransaction().begin();
    }

    public void rollbackTransaction() {
        em.getTransaction().rollback();
    }

    public void commitTransaction() {
        em.getTransaction().commit();
    }

    public Object persist(Object o) {
        em.persist(o);
        return o;
    }

    public void close() {
        em.close();
        emf.close();
    }

    public static void shutdown() {
        if(instance != null) instance.close();
    }
}
