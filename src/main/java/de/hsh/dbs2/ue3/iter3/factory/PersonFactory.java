package de.hsh.dbs2.ue3.iter3.factory;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.ue3.iter3.model.Person;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PersonFactory {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    public static List<Person> findByName(String name) throws Exception {
        name = name == null ? "" : name.trim();

        List<Person> results = new ArrayList<>();

        if(name.length() == 0) {
            Log.debug("Search name is empty. Nothing to do. Returning...");
            return results;
        }

        Log.debug(String.format("Looking for the persons with the name '%s'", name));

        try {
            String movieQuery = new StringBuilder()
                .append("SELECT Id, Name, Sex")
                .append(" FROM Person")
                .append(" WHERE UPPER(Name) LIKE UPPER(?)")
                .toString();

            try (PreparedStatement stmt = Database.getInstance().prepareStatement(movieQuery)) {
                stmt.setString(1, "%" + name.toUpperCase() + "%");
                ResultSet rs = stmt.executeQuery();

                if (!rs.next()) {
                    Log.debug(String.format("No person found for the given name '%s'", name));
                    return results;
                }

                Log.debug(String.format("Person(s) found for the given substring '%s'!", name));

                do {
                    results.add(new Person(
                        rs.getLong("Id"),
                        rs.getString("Name"),
                        rs.getString("Sex").charAt(0)
                    ));

                } while (rs.next());
            }

            return results;
        } catch (Exception e) {
            Log.error("Error occurred during database reading.");
            Log.error(e);
            throw e;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------
}
