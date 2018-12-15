package de.hsh.dbs2.ue4.imdb.logic;

import de.hsh.dbs2.ue3.entity.Person;
import de.hsh.dbs2.ue3.factory.MovieDbEntityManagerFactory;
import de.hsh.dbs2.ue3.factory.PersonFactory;

import java.util.List;
import java.util.stream.Collectors;

public class PersonManager {

	/**
	 * Liefert eine Liste aller Personen, deren Name den Suchstring enthaelt.
	 * @param text Suchstring
	 * @return Liste mit passenden Personennamen, die in der Datenbank eingetragen sind.
	 * @throws Exception
	 */
	public List<String> getPersonList(String text) throws Exception {
        text = text == null ? "" : text.trim();

        try {
            MovieDbEntityManagerFactory.getInstance().startTransaction();
            return PersonFactory.findByName(text, true)
                .stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        } finally {
            MovieDbEntityManagerFactory.getInstance().commitTransaction();
        }
    }

}
