package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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
}
