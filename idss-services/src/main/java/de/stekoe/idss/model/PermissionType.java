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

import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
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
    ;

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
        return permissionTypes;
    }

    public static final Set<PermissionType> forCompany() {
        final Set<PermissionType> permissionTypes = new HashSet<PermissionType>();
        permissionTypes.add(READ);
        permissionTypes.add(UPDATE);
        permissionTypes.add(DELETE);
        permissionTypes.add(MANAGE_MEMBER);
        permissionTypes.add(MANAGE_ROLES);
        return permissionTypes;
    }

}
