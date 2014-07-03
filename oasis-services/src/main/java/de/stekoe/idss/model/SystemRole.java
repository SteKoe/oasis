package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "SystemRole")
public class SystemRole implements Serializable {
    private static final long serialVersionUID = 201404031317L;

    public static final transient String USER = "USER";
    public static final transient String ADMIN = "ADMIN";

    private String id = IDGenerator.createId();
    private String name;

    public SystemRole() {

    }

    public SystemRole(String roleName) {
        setName(roleName);
    }

    @Id
    @Column(name = "system_role_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false, unique = true)
    public String getName() {
        return this.name;
    }

    public void setName(String roleName) {
        this.name = roleName;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof SystemRole)) return false;

        SystemRole that  = (SystemRole) other;
        return new EqualsBuilder()
            .append(getName(), that.getName())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getName())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}