package de.stekoe.idss.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.080+0100")
@StaticMetamodel(SystemRole.class)
public class SystemRole_ {
	public static volatile SingularAttribute<SystemRole, SystemRoleId> id;
	public static volatile SingularAttribute<SystemRole, String> name;
	public static volatile SetAttribute<SystemRole, User> users;
}
