package de.hsh.dbs2.imdb.logic;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.factory.PersonFactory;
import de.hsh.dbs2.ue3.iter3.model.Person;

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

        Database db = Database.getInstance();

        try {
            db.startTransaction();
            return PersonFactory.findByName(text)
                .stream()
                .map(Person::getName)
                .collect(Collectors.toList());
        } finally {
            db.commitTransaction();
        }
    }

}
