package de.stekoe.oasis.model;

public enum PermissionObject implements L10NEnum {
    PROJECT(Project.class, "label.project"),
    COMPANY(Company.class, "label.company");

    private final String key;
    private final Class<?> permissionClazz;

    PermissionObject(Class<?> permissionClazz, String key) {
        this.permissionClazz = permissionClazz;
        this.key = key;
    }

    private Class<?> getValue() {
        return this.permissionClazz;
    }

    public static PermissionObject valueOf(Class<?> value) {
        for (PermissionObject v : values()) {
            if (v.getValue().equals(value)) {
                return v;
            }
        }

        throw new IllegalArgumentException();
    }

    @Override
    public String getKey() {
        return this.key;
    }
}
