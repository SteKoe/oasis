package de.stekoe.idss.model;

import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.048+0100")
@StaticMetamodel(Permission.class)
public class Permission_ {
	public static volatile SingularAttribute<Permission, PermissionId> id;
	public static volatile SingularAttribute<Permission, PermissionObject> permissionObject;
	public static volatile SingularAttribute<Permission, PermissionType> permissionType;
	public static volatile SingularAttribute<Permission, GenericId> objectId;
}
