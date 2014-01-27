package de.stekoe.idss.model.enums;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum PermissionType implements L10NEnum {
    CREATE("permissiontype.create"),
    READ("permissiontype.read"),
    UPDATE("permissiontype.update"),
    DELETE("permissiontype.delete"),
    UPDATE_PROJECT_MEMBER("permissiontype.update.project.member"),
    UPDATE_PROJECT_ROLES("permissiontype.update.project.roles");

    private final String key;

    PermissionType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public static final Set<PermissionType> forReadOnly() {
        final HashSet<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(READ);
        return permissionTypes;
    }

    public static final Set<PermissionType> forCrud() {
        final HashSet<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(CREATE);
        permissionTypes.add(READ);
        permissionTypes.add(UPDATE);
        permissionTypes.add(DELETE);
        return permissionTypes;
    }

    public static final Set<PermissionType> forProject() {
        final HashSet<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.addAll(forCrud());
        permissionTypes.add(UPDATE_PROJECT_MEMBER);
        permissionTypes.add(UPDATE_PROJECT_ROLES);
        return permissionTypes;
    }

}
