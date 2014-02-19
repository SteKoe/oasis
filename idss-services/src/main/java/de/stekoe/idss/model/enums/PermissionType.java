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
    PROJECT_ADD_MEMBER("permissiontype.update.project.member"),
    PROJECT_ADD_ROLES("permissiontype.update.project.roles"),
    PROJECT_UPLOAD_FILE("permissiontype.project.upload.file");

    private final String key;

    PermissionType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public static final Set<PermissionType> forReadOnly() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(READ);
        return permissionTypes;
    }

    public static final Set<PermissionType> forCrud() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(READ);
        permissionTypes.add(UPDATE);
        permissionTypes.add(DELETE);
        return permissionTypes;
    }

    public static final Set<PermissionType> forProject() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.addAll(forCrud());
        permissionTypes.add(PROJECT_ADD_MEMBER);
        permissionTypes.add(PROJECT_ADD_ROLES);
        permissionTypes.add(PROJECT_UPLOAD_FILE);
        return permissionTypes;
    }

}
