package de.hsh.dbs2.ue3.iter3.model.entity;

import de.hsh.dbs2.core.Database;
import de.hsh.dbs2.util.Log;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class StrongEntity extends Entity implements IStrongEntity {

    protected abstract String getSequenceName();

    @Override
    protected PreparedStatement prepareUpdateStatement() throws SQLException {
        Map<String, Object> dbFieldMap = getDbFieldMap();
        List<String> dbUpdateFields = new ArrayList<>();


        for (Map.Entry e : dbFieldMap.entrySet()) {
            dbUpdateFields.add(String.format("%s = %s", e.getKey(), e.getValue() == null ? "NULL" : "?"));
        }

        String updateQuery = new StringBuilder()
            .append("UPDATE " + getTableName())
            .append(String.format(" SET %s", String.join(", ", dbUpdateFields)))
            .append(" WHERE Id = ?")
            .toString();

        Log.debug(updateQuery, "|", this);
        Database db = Database.getInstance();
        PreparedStatement stmt = db.prepareStatement(updateQuery);
        int stmtIndex = db.bindDbFieldValues(stmt, dbFieldMap);
        stmt.setLong(stmtIndex + 1, getId());

        return stmt;
    }

    @Override
    protected PreparedStatement prepareDeleteStatement() throws SQLException {
        String deleteQuery = new StringBuilder()
            .append("DELETE FROM " + getTableName())
            .append(" WHERE Id = ?")
            .toString();

        Log.debug(deleteQuery, "|", this);
        PreparedStatement stmt = Database.getInstance().prepareStatement(deleteQuery);
        stmt.setLong(1, getId());
        return stmt;
    }

    @Override
    public Long insert() throws SQLException {
        if (!doInsert()) {
            Log.error(String.format("Insertion of %s failed, no rows affected. [%s]", this.getTableName(), this.toString()));
            return null;
        }

        try (PreparedStatement ps = Database.getInstance().prepareStatement(String.format("SELECT %s.currval FROM DUAL", getSequenceName()))) {
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                this.setId(rs.getLong(1));
            }
        }
        Log.info(String.format("%s record inserted! [%s]", this.getTableName(), this.toString()));
        return this.getId();
    }

    @Override
    public boolean delete() throws SQLException {
        if (super.delete()) {
            this.setId(null);
            return true;
        }
        return false;
    }
}
