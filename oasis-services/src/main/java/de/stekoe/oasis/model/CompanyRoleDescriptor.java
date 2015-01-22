package de.stekoe.oasis.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class CompanyRoleDescriptor implements Serializable {
    private static final long serialVersionUID = 20141220L;
    private String id;
    private String name;
    private Set<String> permissions = new HashSet<>();

    public CompanyRoleDescriptor() {
        // NOP
    }

    public CompanyRoleDescriptor(CompanyRole companyRole) {
        setId(companyRole.getId());
        setName(companyRole.getName());
        companyRole.getPermissions().stream().map(p -> (Permission)p).forEach(p -> {
            permissions.add(p.getPermissionType().toString());
        });
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
    public Set<String> getPermissions() {
        return permissions;
    }
    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }
}
