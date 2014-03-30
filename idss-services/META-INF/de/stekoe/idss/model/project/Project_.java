package de.stekoe.idss.model.project;

import de.stekoe.idss.model.Document;
import de.stekoe.idss.model.criterion.scale.Scale;
import de.stekoe.idss.model.enums.ProjectStatus;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:48:29.904+0100")
@StaticMetamodel(Project.class)
public class Project_ {
	public static volatile SingularAttribute<Project, ProjectId> id;
	public static volatile SingularAttribute<Project, String> name;
	public static volatile SingularAttribute<Project, String> description;
	public static volatile SetAttribute<Project, ProjectMember> projectTeam;
	public static volatile SetAttribute<Project, Document> documents;
	public static volatile SetAttribute<Project, ProjectRole> projectRoles;
	public static volatile ListAttribute<Project, Scale> scaleList;
	public static volatile SingularAttribute<Project, ProjectStatus> projectStatus;
	public static volatile SingularAttribute<Project, Date> projectStartDate;
	public static volatile SingularAttribute<Project, Date> projectEndDate;
}
