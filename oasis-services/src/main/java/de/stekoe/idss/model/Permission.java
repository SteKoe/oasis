package de.stekoe.idss.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


@Entity
@Table(name = "Permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 101403011956L;

    private String id = IDGenerator.createId();
    private PermissionObject permissionObject;
    private PermissionType permissionType;
    private String objectId;

    public Permission() {
        // NOP
    }

    public Permission(PermissionObject permissionObject, PermissionType permissionType, String objectId) {
        this.permissionObject = permissionObject;
        this.permissionType = permissionType;
        this.objectId = objectId;
    }

    @Id
    public String getId() {
        return id;
    }

    public void setId(String id) {
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
        return !StringUtils.isBlank(getObjectId());
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof Permission)) return false;

        Permission that  = (Permission) other;
        return new EqualsBuilder()
            .appendSuper(super.equals(other))
            .append(getId(), that.getId())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
            .append(getId())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
