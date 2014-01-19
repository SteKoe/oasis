package de.stekoe.idss.model;

import de.stekoe.idss.IDGenerator;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Entity
@Table(name = "Permission")
public class Permission implements Serializable {

    private String id = IDGenerator.createId();
    private PermissionObject objectType;
    private PermissionType permissionType;
    private String objectId;

    public Permission() {}

    public Permission(PermissionObject objectType, PermissionType permissionType, String objectId) {
        setObjectType(objectType);
        setPermissionType(permissionType);
        setObjectId(objectId);
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

    // =====

    public static Set<Permission> createAll(PermissionObject objectType, String objectId) {
        Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(new Permission(objectType, PermissionType.CREATE, objectId));
        permissions.add(new Permission(objectType, PermissionType.READ, objectId));
        permissions.add(new Permission(objectType, PermissionType.UPDATE, objectId));
        permissions.add(new Permission(objectType, PermissionType.DELETE, objectId));
        return permissions;
    }

    public static Set<Permission> createReadOnly(PermissionObject objectType, String objectId) {
        Set<Permission> permissions = new HashSet<Permission>();
        permissions.add(new Permission(objectType, PermissionType.READ, objectId));
        return permissions;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
