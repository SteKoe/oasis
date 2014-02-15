package de.stekoe.idss.model.project;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.Permission;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "ProjectRole")
public class ProjectRole implements Serializable {

    private String id = IDGenerator.createId();
    private String name;
    private Set<Permission> permissions = new HashSet<Permission>();

    @Id
    @Column(name = "project_role_id")
    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotNull
    @Column(nullable = false)
    public String getName() {
        return this.name;
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
        return this.name;
    }

}