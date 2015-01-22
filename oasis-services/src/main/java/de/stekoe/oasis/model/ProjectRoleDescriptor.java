package de.stekoe.oasis.model;

import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectRoleDescriptor implements Serializable {
    private String id;
    private String name;
    private List<String> permissions = new ArrayList<>();

    public ProjectRoleDescriptor() {
        // NOP
    }

    public ProjectRoleDescriptor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public ProjectRoleDescriptor(ProjectRole projectRole) {
        this.id = projectRole.getId();
        this.name = projectRole.getName();

        for (Permission p : projectRole.getPermissions()) {
            permissions.add(p.getPermissionType().toString());
        }

//        this.permissions = projectRole.getPermissions().stream().map(perm -> perm.getPermissionType()).collect(Collectors.toList());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NotEmpty
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<String> permissions) {
        this.permissions = permissions;
    }
}
