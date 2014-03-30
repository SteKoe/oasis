package de.stekoe.idss.model.project;

import de.stekoe.idss.model.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.065+0100")
@StaticMetamodel(ProjectMember.class)
public class ProjectMember_ {
	public static volatile SingularAttribute<ProjectMember, ProjectMemberId> id;
	public static volatile SingularAttribute<ProjectMember, User> user;
	public static volatile SingularAttribute<ProjectMember, ProjectRole> projectRole;
	public static volatile SetAttribute<ProjectMember, ProjectMemberGroup> projectGroups;
}
