package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.SystemRole;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class ProjectRoleDAO extends GenericDAO implements IProjectRoleDAO {

    @Override
    public void update(ProjectRole role) {
        getCurrentSession().update(role);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<ProjectRole> getAllRoles() {
        return getCurrentSession().createCriteria(SystemRole.class).list();
    }

    @Override
    public boolean save(ProjectRole role) {
        try {
            getCurrentSession().save(role);
        } catch (HibernateException he) {
            return false;
        }
        return true;
    }

    @Override
    public ProjectRole getRoleByName(String rolename) {
        return (ProjectRole) getCurrentSession().createCriteria(ProjectRole.class).add(Restrictions.eq("name", rolename)).uniqueResult();
    }

}
