package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.SystemRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class ProjectRoleDAO extends GenericDAO implements IProjectRoleDAO {

    @Override
    public void save(ProjectRole role) {
        getCurrentSession().update(role);
    }

    @Override
    public void delete(Serializable id) {
        final ProjectRole role = findById(id);
        delete(role);
    }

    @Override
    public void delete(ProjectRole entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public ProjectRole findById(Serializable id) {
        return (ProjectRole) getCurrentSession().get(SystemRole.class, id);
    }

    @Override
    public List<ProjectRole> findAll() {
        return getCurrentSession().createCriteria(SystemRole.class).list();
    }

    @Override
    public ProjectRole getRoleByName(String rolename) {
        final Criteria criteria = getCurrentSession().createCriteria(ProjectRole.class);
        criteria.add(Restrictions.eq("name", rolename));
        return (ProjectRole) criteria.uniqueResult();
    }

}
