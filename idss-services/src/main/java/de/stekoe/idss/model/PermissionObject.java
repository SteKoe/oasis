/*
 * Copyright 2014 Stephan Koeninger
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

package de.stekoe.idss.model;

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
