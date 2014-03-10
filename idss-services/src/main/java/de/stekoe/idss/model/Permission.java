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

package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 101403011956L;

    private PermissionId id = new PermissionId();
    private PermissionObject permissionObject;
    private PermissionType permissionType;
    private GenericId objectId;

    public Permission() {
        // NOP
    }

    public Permission(PermissionObject permissionObject, PermissionType permissionType, GenericId objectId) {
        this.permissionObject = permissionObject;
        this.permissionType = permissionType;
        this.objectId = objectId;
    }

    @EmbeddedId
    public PermissionId getId() {
        return id;
    }

    public void setId(PermissionId id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PermissionObject getPermissionObject() {
        return permissionObject;
    }

    public void setPermissionObject(PermissionObject objectType) {
        this.permissionObject = objectType;
    }

    public void setPermissionType(PermissionType permissionType) {
        this.permissionType = permissionType;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PermissionType getPermissionType() {
        return permissionType;
    }

    public boolean hasObjectId() {
        return getObjectId() != null;
    }

    public GenericId getObjectId() {
        return objectId;
    }

    public void setObjectId(GenericId objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return this.getPermissionType().getKey();
    }
}
