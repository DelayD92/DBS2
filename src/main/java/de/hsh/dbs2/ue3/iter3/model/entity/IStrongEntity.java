package de.hsh.dbs2.ue3.iter3.model.entity;

import java.sql.SQLException;

public interface IStrongEntity extends IEntity {

    Long getId();

    void setId(Long id);

    Long insert() throws SQLException;
}
