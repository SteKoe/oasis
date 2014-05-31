package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
public class CompanyRole implements Serializable {
    private static final long serialVersionUID = 201404131428L;
    private String id = IDGenerator.createId();
    private String name;
    private Set<Permission> permissions = new HashSet<Permission>();

    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Basic
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(targetEntity = Permission.class, cascade = CascadeType.ALL)
    public Set<Permission> getPermissions() {
        return permissions;
    }
    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }

        CompanyRole that = (CompanyRole) o;
        return new EqualsBuilder()
                .append(getName(), that.getName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getName())
                .hashCode();
    }
}
