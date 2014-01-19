package de.stekoe.idss.model.enums;

import de.stekoe.idss.model.Project;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum PermissionObject {
    PROJECT(Project.class);

    private Class<?> projectClass;

    PermissionObject(Class<?> projectClass) {
        this.projectClass = projectClass;
    }

    private Class<?> getValue() {
        return this.projectClass;
    }

    public static PermissionObject valueOf(Class<?> value) {
        for(PermissionObject v : values()) {
            if(v.getValue().equals(value)) {
                return v;
            }
        }

        throw new IllegalArgumentException();
    }
}
