package de.stekoe.idss.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum PermissionType implements L10NEnum {
    ALL("permissiontype.all"),
    CREATE("permissiontype.create"),
    READ("permissiontype.read"),
    UPDATE("permissiontype.update"),
    DELETE("permissiontype.delete"),
    MANAGE_MEMBER("permissiontype.manage.member"),
    MANAGE_ROLES("permissiontype.manage.roles"),
    UPLOAD_FILE("permissiontype.project.upload.file"),
    MANAGE_ADDRESSES("permissiontype.manage.addresses"),
    MANAGE_CRITERIONS("permissiontype.manage.criterions"),;

    private final String key;

    PermissionType(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    public static PermissionType getByKey(String key) {
        PermissionType[] values = PermissionType.values();
        return Arrays.<PermissionType>stream(values).filter(pt -> pt.getKey().equals(key)).findFirst().get();
    }

    ;

    public static final Set<PermissionType> forReadOnly() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(READ);
        return permissionTypes;
    }

    public static final Set<PermissionType> forCrud() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(CREATE);
        permissionTypes.add(READ);
        permissionTypes.add(UPDATE);
        permissionTypes.add(DELETE);
        return permissionTypes;
    }

    public static final Set<PermissionType> forProject() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(READ);
        permissionTypes.add(UPDATE);
        permissionTypes.add(DELETE);
        permissionTypes.add(MANAGE_MEMBER);
        permissionTypes.add(MANAGE_ROLES);
        permissionTypes.add(UPLOAD_FILE);
        permissionTypes.add(MANAGE_CRITERIONS);
        return permissionTypes;
    }

    public static final Set<PermissionType> forCompany() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(ALL);
        permissionTypes.add(READ);
        permissionTypes.add(UPDATE);
        permissionTypes.add(DELETE);
        permissionTypes.add(MANAGE_MEMBER);
        permissionTypes.add(MANAGE_ADDRESSES);
        permissionTypes.add(MANAGE_ROLES);
        return permissionTypes;
    }

}
