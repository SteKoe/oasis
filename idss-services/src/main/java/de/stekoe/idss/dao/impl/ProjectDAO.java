package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class ProjectDAO extends GenericDAO implements IProjectDAO {

    @Override
    public void save(Project project) {
        getCurrentSession().save(project);
    }

    @Override
    public void delete(Serializable id) {
        final Project project = findById(id);
        delete(project);
    }

    @Override
    public List<Project> findByProjectName(String projectName) {
        Criteria criteria = getCurrentSession().createCriteria(Project.class);
        criteria.add(Restrictions.eq("name", projectName));
        return criteria.list();
    }

    @Override
    public Project findById(Serializable id) {
        return (Project) getCurrentSession().get(Project.class, id);
    }

    @Override
    public void delete(Project entity) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Project> findAll() {
        return getCurrentSession().createCriteria(Project.class).list();
    }

    @Override
    public List<Project> findAllForUser(User user) {
        final Criteria criteria = getCurrentSession().createCriteria(Project.class);
        criteria.createCriteria("projectTeam").add(Restrictions.eq("user", user));
        return criteria.list();
    }

}
