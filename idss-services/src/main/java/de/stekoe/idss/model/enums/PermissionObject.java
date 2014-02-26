package de.stekoe.idss.model.enums;

import de.stekoe.idss.model.project.Project;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum PermissionObject implements L10NEnum {
    PROJECT(Project.class, "label.project");

    private final String key;
    private final Class<?> projectClass;

    PermissionObject(Class<Project> projectClass, String key) {
        this.projectClass = projectClass;
        this.key = key;
    }

    private Class<?> getValue() {
        return this.projectClass;
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
