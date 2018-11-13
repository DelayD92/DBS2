package de.hsh.dbs2.core;

import de.hsh.dbs2.utils.Log;

import java.sql.*;

public class Database {

    private static Database dbInstance;
    private Connection connection = null;

    public static void init(Config.DB dbConfig) throws Exception {
        try {
            Database db = new Database();

            Log.info("Loading JDBC driver...");
            Class.forName(dbConfig.driver);
            Log.info("JDBC driver loaded successfully!");

            Log.info("Connecting to database...");
            db.connection = DriverManager.getConnection(dbConfig.connectionString, dbConfig.user.name, dbConfig.user.password);
            Log.info("Database connection established!");

            dbInstance = db;
        } catch (ClassNotFoundException e) {
            Log.error("Loading JDBC driver failed!");
            throw e;
        } catch (SQLException e) {
            Log.error("Ups, can not connect to the database!");
            throw e;
        }
    }

    public static Database getInstance() {
        if (dbInstance == null) {
            Log.error("Database not initialized. Please call init(dbConfig) first!");
        }

        return dbInstance;
    }

    public Connection getConnection() {
        return connection;
    }

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query);
    }
}
