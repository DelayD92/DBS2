package de.hsh.dbs2.ue3.iter3.model.entity;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.util.Log;

import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class Entity implements IEntity {

    protected abstract String getTableName();

    protected abstract String[] getDbFields();

    protected Map<String, Object> getDbFieldMap() {
        Map<String, Object> dbFieldMap = new HashMap<>();

        for (String field : getDbFields()) {
            try {
                Method method = this.getClass().getMethod(String.format("get%s", field));
                Object value = method.invoke(this);
                dbFieldMap.put(field, value);
            } catch (Exception e) {
                Log.error(e);
                return null;
            }
        }

        return dbFieldMap;
    }

    protected PreparedStatement prepareInsertStatement() throws SQLException {
        Map<String, Object> dbFieldMap = getDbFieldMap();
        List<String> dbInsertFields = new ArrayList<>();

        for (Map.Entry e : dbFieldMap.entrySet()) {
            if (e.getValue() == null) continue;
            dbInsertFields.add(e.getKey().toString());
        }

        String insertQuery = new StringBuilder()
            .append("INSERT INTO " + getTableName())
            .append(String.format(" (%s)", String.join(", ", dbInsertFields)))
            .append(String.format(" VALUES (%s)", String.join(", ", dbInsertFields.stream().map(e -> "?").collect(Collectors.toList()))))
            .toString();

        Log.debug(insertQuery, "|", this);
        Database db = Database.getInstance();
        PreparedStatement stmt = db.prepareStatement(insertQuery);
        db.bindDbFieldValues(stmt, dbFieldMap);
        return stmt;
    }

    protected abstract PreparedStatement prepareDeleteStatement() throws SQLException;

    protected abstract PreparedStatement prepareUpdateStatement() throws SQLException;

    protected boolean doInsert() throws SQLException {
        try (PreparedStatement stmt = this.prepareInsertStatement()) {
            int affectedRows = stmt.executeUpdate();
            return affectedRows != 0;
        }
    }

    protected boolean doDelete() throws SQLException {
        try (PreparedStatement stmt = this.prepareDeleteStatement()) {
            int affectedRows = stmt.executeUpdate();
            return affectedRows != 0;
        }
    }

    protected boolean doUpdate() throws SQLException {
        try (PreparedStatement stmt = this.prepareUpdateStatement()) {
            int affectedRows = stmt.executeUpdate();
            return affectedRows != 0;
        }
    }

    @Override
    public boolean delete() throws SQLException {
        if (!doDelete()) {
            Log.error(String.format("Deletion of %s failed, no rows affected. [%s]", this.getTableName(), this.toString()));
            return false;
        } else {
            Log.info(String.format("%s record deleted successfully. [%s]", this.getTableName(), this.toString()));
            return true;
        }
    }

    @Override
    public boolean update() throws SQLException {
        if (doUpdate()) {
            Log.info(String.format("%s record updated successfully. [%s]", this.getTableName(), this.toString()));
            return true;
        } else {
            Log.error(String.format("Update of %s failed, no rows affected. [%s]", this.getTableName(), this.toString()));
            return false;
        }
    }
}
