package de.hsh.dbs2.core;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import de.hsh.dbs2.utils.Log;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    public Log.LogLevel logLevel;
    public DB db;

    public static final class Loader {
        private static final String CONF_FILE_PATH = "/options/settings.json";

        public static Config loadConfigs() throws FileNotFoundException {
            try {
                Log.debug("Loading configs from file", CONF_FILE_PATH);
                InputStream is = Loader.class.getResourceAsStream(CONF_FILE_PATH);
                if (is == null) {
                    throw new FileNotFoundException();
                }
                JsonReader reader = new JsonReader(new InputStreamReader(is));
                return new Gson().fromJson(reader, Config.class);
            } catch (Exception e) {
                System.out.printf("Error loading config file %s. Please make sure it exists.\n", CONF_FILE_PATH);
                throw e;
            }
        }
    }

    public class DB {
        public String driver;
        public String connectionString;
        public User user;

        public class User {
            public String name;
            public String password;
        }
    }
}
