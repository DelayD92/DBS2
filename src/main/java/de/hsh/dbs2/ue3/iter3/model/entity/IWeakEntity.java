package de.hsh.dbs2.ue3.iter3.model.entity;

import java.sql.SQLException;

public interface IWeakEntity extends IEntity {
    boolean insert() throws SQLException;
}
