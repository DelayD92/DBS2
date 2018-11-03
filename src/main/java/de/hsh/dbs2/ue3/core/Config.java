package de.hsh.dbs2.ue3.core;

import de.hsh.dbs2.ue3.utils.Log;

public class Config {

    public Log.LogLevel logLevel;
    public DB db;

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
