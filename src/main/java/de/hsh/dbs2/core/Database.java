package de.hsh.dbs2.core;

import de.hsh.dbs2.util.Log;

import java.sql.*;
import java.util.Map;

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

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public void startTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        connection.commit();
    }

    public void rollbackTransaction() throws SQLException {
        connection.rollback();
    }

    public Statement createStatement() throws SQLException {
        return this.connection.createStatement();
    }

    public PreparedStatement prepareStatement(String query) throws SQLException {
        return this.connection.prepareStatement(query);
    }

    public int bindDbFieldValues(PreparedStatement stmt, Map<String, Object> dbFieldMap, Integer... initialIndex) throws SQLException {
        int stmtIndex = initialIndex.length > 0 ? initialIndex[0] : 0;

        for (Map.Entry e : dbFieldMap.entrySet()) {
            if (e.getValue() == null) continue;

            Object value = e.getValue();

            stmtIndex++;

            if (value.getClass() == String.class || value.getClass() == Character.class) {
                stmt.setString(stmtIndex, String.valueOf(value));
            } else if (value.getClass() == Integer.class) {
                stmt.setInt(stmtIndex, (Integer) value);
            } else if (value.getClass() == Long.class) {
                stmt.setLong(stmtIndex, (Long) value);
            }
        }
        return stmtIndex;
    }
}
