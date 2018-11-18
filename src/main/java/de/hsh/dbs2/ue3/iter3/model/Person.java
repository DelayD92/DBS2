package de.hsh.dbs2.ue3.iter3.model;

import de.hsh.dbs2.ue3.iter3.model.entity.StrongEntity;

public class Person extends StrongEntity {

    private Long id;
    private String name;
    private Character sex;

    public Person() {
    }

    public Person(String name, Character sex) {
        this.name = name;
        this.sex = sex;
    }

    public Person(Long id, String name, Character sex) {
        this.id = id;
        this.name = name;
        this.sex = sex;
    }

    protected String[] getDbFields() {
        return new String[]{"Name", "Sex"};
    }

    @Override
    protected String getTableName() {
        return "Person";
    }

    @Override
    protected String getSequenceName() {
        return "person_sequence";
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Character getSex() {
        return sex;
    }

    public void setSex(Character sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Name: %s, Sex: %c", id, name, sex);
    }
}
