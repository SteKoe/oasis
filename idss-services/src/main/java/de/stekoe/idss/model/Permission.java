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
public class  Permission implements Serializable {

    private String id = IDGenerator.createId();
    private PermissionObject objectType;
    private PermissionType permissionType;
    private String objectId;

    public Permission() {
        // NOP
    }

    public Permission(PermissionObject objectType, PermissionType permissionType, String objectId) {
        this.objectType = objectType;
        this.permissionType = permissionType;
        this.objectId = objectId;
    }

    @Id
    @Column(name = "permission_id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    public PermissionObject getObjectType() {
        return objectType;
    }

    public void setObjectType(PermissionObject objectType) {
        this.objectType = objectType;
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
        return !StringUtils.isBlank(getObjectId());
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public String toString() {
        return this.getPermissionType().getKey();
    }
}
