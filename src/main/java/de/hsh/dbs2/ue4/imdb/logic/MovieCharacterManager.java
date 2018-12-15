package de.hsh.dbs2.ue4.imdb.logic;

import de.hsh.dbs2.ue3.entity.MovieCharacter;
import de.hsh.dbs2.ue3.factory.MovieCharacterFactory;
import de.hsh.dbs2.ue3.factory.MovieDbEntityManagerFactory;

import java.util.List;

public class MovieCharacterManager {

    public MovieCharacter findByCharacter(String name) throws Exception {
        List<MovieCharacter> movChars = findByCharacter(name, false);
        return movChars.size() > 0 ? movChars.get(0) : null;
    }

	public List<MovieCharacter> findByCharacter(String name, boolean globalSearch) throws Exception {
        name = name == null ? "" : name.trim();

        try {
            MovieDbEntityManagerFactory.getInstance().startTransaction();
            return MovieCharacterFactory.findByCharacter(name, globalSearch);
        } finally {
            MovieDbEntityManagerFactory.getInstance().commitTransaction();
        }
    }

}
