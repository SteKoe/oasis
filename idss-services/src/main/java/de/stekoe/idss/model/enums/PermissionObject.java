/*
 * Copyright 2014 Stephan Köninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
