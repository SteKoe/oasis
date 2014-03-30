package de.stekoe.idss.model;

import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.model.project.ProjectMember;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.091+0100")
@StaticMetamodel(User.class)
public class User_ {
	public static volatile SingularAttribute<User, UserId> id;
	public static volatile SingularAttribute<User, UserProfile> profile;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, String> activationKey;
	public static volatile SetAttribute<User, SystemRole> roles;
	public static volatile SingularAttribute<User, UserStatus> userStatus;
	public static volatile SetAttribute<User, ProjectMember> projectMemberships;
	public static volatile SetAttribute<User, Permission> permissions;
}
