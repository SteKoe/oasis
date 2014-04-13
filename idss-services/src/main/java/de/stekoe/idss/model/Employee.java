package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Employee implements Serializable {
    private static final long serialVersionUID = 201404132211L;

    private String id = IDGenerator.createId();
    private User user;
    private CompanyRole role;

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = User.class)
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(targetEntity = CompanyRole.class)
    public CompanyRole getRole() {
        return role;
    }
    public void setRole(CompanyRole role) {
        this.role = role;
    }
}
