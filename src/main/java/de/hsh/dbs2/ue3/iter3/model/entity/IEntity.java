package de.hsh.dbs2.ue3.iter3.model.entity;

import java.sql.SQLException;

public interface IEntity {
    boolean update() throws SQLException;

    boolean delete() throws SQLException;
}
