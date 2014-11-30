package de.stekoe.idss.model;

import java.io.Serializable;
import java.util.Set;

public class ProjectRoleDescriptor implements Serializable {
    private String id;
    private String name;
    private Set<Permission> permissions;

    public ProjectRoleDescriptor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectRoleDescriptor(ProjectRole projectRole) {
        this.id = projectRole.getId();
        this.name = projectRole.getName();
        this.permissions = projectRole.getPermissions();
    }

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

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}
