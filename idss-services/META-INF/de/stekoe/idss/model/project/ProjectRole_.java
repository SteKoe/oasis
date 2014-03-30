package de.stekoe.idss.model.project;

import de.stekoe.idss.model.Permission;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.075+0100")
@StaticMetamodel(ProjectRole.class)
public class ProjectRole_ {
	public static volatile SingularAttribute<ProjectRole, ProjectRoleId> id;
	public static volatile SingularAttribute<ProjectRole, String> name;
	public static volatile SetAttribute<ProjectRole, Permission> permissions;
}
