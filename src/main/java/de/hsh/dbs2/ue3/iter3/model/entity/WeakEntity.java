package de.hsh.dbs2.ue3.iter3.model.entity;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public abstract class WeakEntity extends Entity implements IWeakEntity {

    private Map<String, Object> _dbShadowFieldMap_ = new HashMap<>();

    private void saveShadowFields() {
        for (Map.Entry e : getDbFieldMap().entrySet()) {
            _dbShadowFieldMap_.put(e.getKey().toString(), e.getValue());
        }
    }

    private Map<String, Object> getShadowFields() {
        return _dbShadowFieldMap_;
    }

    @Override
    protected PreparedStatement prepareUpdateStatement() throws SQLException {
        Map<String, Object> dbNewFieldMap = getDbFieldMap();
        List<String> dbNewUpdateFields = new ArrayList<>();


        for (Map.Entry e : dbNewFieldMap.entrySet()) {
            dbNewUpdateFields.add(String.format("%s = %s", e.getKey(), e.getValue() == null ? "NULL" : "?"));
        }

        String updateQuery = new StringBuilder()
            .append("UPDATE " + getTableName())
            .append(String.format(" SET %s", String.join(", ", dbNewUpdateFields)))
            .append(String.format(" WHERE %s", String.join(" AND ", dbNewUpdateFields)))
            .toString();

        Log.debug(updateQuery, "|", this);
        Database db = Database.getInstance();
        PreparedStatement stmt = db.prepareStatement(updateQuery);
        int stmtIndex = db.bindDbFieldValues(stmt, dbNewFieldMap);

        Map<String, Object> dbOldFieldMap = getShadowFields();
        db.bindDbFieldValues(stmt, dbOldFieldMap, stmtIndex);

        return stmt;
    }

    @Override
    protected PreparedStatement prepareDeleteStatement() throws SQLException {
        Map<String, Object> dbFieldMap = getDbFieldMap();
        List<String> dbInsertFields = new ArrayList<>();

        for (Map.Entry e : dbFieldMap.entrySet()) {
            if (e.getValue() == null) continue;
            dbInsertFields.add(e.getKey().toString());
        }

        String deleteQuery = new StringBuilder()
            .append("DELETE FROM " + getTableName())
            .append(String.format(" WHERE %s", String.join(" AND ", dbInsertFields.stream().map(e -> e + " = ?").collect(Collectors.toList()))))
            .toString();

        Log.debug(deleteQuery, "|", this);
        Database db = Database.getInstance();
        PreparedStatement stmt = db.prepareStatement(deleteQuery);
        db.bindDbFieldValues(stmt, dbFieldMap);
        return stmt;
    }

    @Override
    public boolean insert() throws SQLException {
        if (super.doInsert()) {
            Log.info(String.format("%s record inserted! [%s]", this.getTableName(), this.toString()));
            this.saveShadowFields();
            return true;
        } else {
            Log.error(String.format("Insertion of %s failed, no rows affected. [%s]", this.getTableName(), this.toString()));
            return false;
        }
    }

    @Override
    public boolean update() throws SQLException {
        if (super.update()) {
            this.saveShadowFields();
            return true;
        }

        return false;
    }
}
