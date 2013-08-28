package de.stekoe.idss.model;

// Generated 26.08.2013 06:13:04 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Role implements java.io.Serializable {

    private static final Role USER = new Role(Roles.USER);
    private static final Role ADMIN = new Role(Roles.ADMIN);

    @Id
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    private String roleName;

    @OneToMany
    private Set<User> users = new HashSet<User>(0);

    public Role() {
    }

    public Role(String roleName, Set<User> users) {
        this.roleName = roleName;
        this.users = users;
    }

    public Role(String roleName) {
        this.roleName = roleName;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoleName() {
        return this.roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<User> getUsers() {
        return this.users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}
