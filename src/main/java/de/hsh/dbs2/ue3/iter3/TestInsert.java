package de.hsh.dbs2.ue3.iter3;

import de.hsh.dbs2.core.Config;
import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.util.Log;

import java.sql.SQLException;

public class TestInsert {

    public static void main(String... args) {
        try {
            bootstrap();
            run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void bootstrap(String... args) throws Exception {
        Config config = Config.Loader.loadConfigs();
        Log.setLogLevel(config.logLevel);
        Database.init(config.db);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------

    private static void run(String... args) {
        Database db = Database.getInstance();

        TestFactory test = new TestFactory();

        try {
            db.startTransaction();

            Log.info("Starting insert test...");
            test.insert();
            Log.info("Insert test succeed!\n\n");

            Log.info("Starting MovieFactory test 1...");
            test.read();
            Log.info("MovieFactory test 1 succeed!\n\n");

            Log.info("Starting update test...");
            test.update();
            Log.info("Update test succeed!\n\n");

            Log.info("Starting MovieFactory test 2...");
            test.read();
            Log.info("MovieFactory test 2 succeed!\n\n");

            Log.info("Starting delete test...");
            test.delete();
            Log.info("Delete test succeed!\n\n");

            Log.info("Starting MovieFactory test 3...");
            test.read();
            Log.info("MovieFactory test 3 succeed!\n\n");

            db.commitTransaction();
        } catch (Exception e) {
            Log.error("Error occurred during model test. Rolling back.");
            Log.error(e);

            try {
                db.rollbackTransaction();
            } catch (SQLException e2) {
                Log.error(e2);
            }
        } finally {
            try {
                db.closeConnection();
            } catch (SQLException e) {
                Log.error(e);
            }
        }
    }
}
