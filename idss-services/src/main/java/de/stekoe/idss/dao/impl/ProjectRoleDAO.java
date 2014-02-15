package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.project.ProjectRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class ProjectRoleDAO extends GenericDAO<ProjectRole> implements IProjectRoleDAO {

    @Override
    public ProjectRole getRoleByName(String rolename) {
        final Criteria criteria = getSession().createCriteria(ProjectRole.class);
        criteria.add(Restrictions.eq("name", rolename));
        return (ProjectRole) criteria.uniqueResult();
    }

    @Override
    protected Class<ProjectRole> getPersistedClass() {
        return ProjectRole.class;
    }
}
