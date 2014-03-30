package de.stekoe.idss.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.098+0100")
@StaticMetamodel(UserProfile.class)
public class UserProfile_ {
	public static volatile SingularAttribute<UserProfile, UserProfileId> id;
	public static volatile SingularAttribute<UserProfile, String> firstname;
	public static volatile SingularAttribute<UserProfile, String> surname;
	public static volatile SingularAttribute<UserProfile, Date> birthdate;
}
