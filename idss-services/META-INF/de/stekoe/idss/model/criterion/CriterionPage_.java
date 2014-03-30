package de.stekoe.idss.model.criterion;

import de.stekoe.idss.model.project.Project;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2014-03-29T20:47:28.010+0100")
@StaticMetamodel(CriterionPage.class)
public class CriterionPage_ {
	public static volatile SingularAttribute<CriterionPage, CriterionPageId> id;
	public static volatile SingularAttribute<CriterionPage, Integer> ordering;
	public static volatile ListAttribute<CriterionPage, CriterionPageElement> pageElements;
	public static volatile SingularAttribute<CriterionPage, Project> project;
	public static volatile SingularAttribute<CriterionPage, String> name;
}
