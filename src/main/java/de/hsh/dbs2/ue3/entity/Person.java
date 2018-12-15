package de.hsh.dbs2.ue3.entity;

import org.hibernate.annotations.Check;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = "name", name="uk_person_name")
})
@SelectBeforeUpdate
@DynamicUpdate
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false)
    @Check(constraints = "Sex IN ('M', 'W')")
    private char sex;

    @OneToMany(mappedBy = "person", fetch=FetchType.LAZY)
    private Set<MovieCharacter> roles = new HashSet<>();

    public Person() {
    }

    public Person(String name, char sex) {
        this.name = name;
        this.sex = sex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getSex() {
        return sex;
    }

    public void setSex(char sex) {
        this.sex = sex;
    }

    public Set<MovieCharacter> getRoles() {
        return roles;
    }

    public void setRoles(Set<MovieCharacter> plays) {
        this.roles = plays;
    }

    public void addRole(MovieCharacter ...roles) {
        for(MovieCharacter role : roles) {
            if(this.roles.stream().noneMatch(r -> r.getId() == role.getId())) {
                this.roles.add(role);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Id: %d, Name: %s, Sex: %c", id, name, sex);
    }
}
