package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

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

    @Transient
    public boolean hasRole(CompanyRole role) {
        if(role == null) {
            return false;
        }
        return getRole().getName().equals(role.getName());
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Employee)) return false;

        Employee that  = (Employee) other;
        return new EqualsBuilder()
            .appendSuper(super.equals(other))
            .append(getId(), that.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
