package de.stekoe.oasis.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Permission")
public class Permission implements Serializable {

    private static final long serialVersionUID = 101403011956L;

    private String id;
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
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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
        if (this == other) return true;
        if (!(other instanceof Permission)) return false;

        Permission that = (Permission) other;
        return new EqualsBuilder()
                .append(getObjectId(), that.getObjectId())
                .append(getPermissionObject(), that.getPermissionObject())
                .append(getPermissionType(), that.getPermissionType())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getObjectId())
                .append(getPermissionObject())
                .append(getPermissionType())
                .toHashCode();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
