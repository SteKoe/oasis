package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.project.ProjectRole;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class ProjectRoleDAO extends GenericDAO implements IProjectRoleDAO {

    @Override
    public void save(ProjectRole role) {
        getCurrentSession().saveOrUpdate(role);
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
        return (ProjectRole) getCurrentSession().get(ProjectRole.class, id);
    }

    @Override
    public List<ProjectRole> findAll() {
        return getCurrentSession().createCriteria(ProjectRole.class).list();
    }

    @Override
    public ProjectRole getRoleByName(String rolename) {
        final Criteria criteria = getCurrentSession().createCriteria(ProjectRole.class);
        criteria.add(Restrictions.eq("name", rolename));
        return (ProjectRole) criteria.uniqueResult();
    }
}
