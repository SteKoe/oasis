package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class ProjectDAO extends GenericDAO implements IProjectDAO {

    @Override
    public boolean save(Project project) {
        getCurrentSession().saveOrUpdate(project);
        return false;
    }

    @Override
    public List<Project> findByProjectName(String projectName) {
        Criteria criteria = getCurrentSession().createCriteria(Project.class);
        criteria.add(Restrictions.eq("name", projectName));
        return criteria.list();
    }

    @Override
    public Project findById(String id) {
        return (Project) getCurrentSession().get(Project.class, id);
    }

    @Override
    public List<Project> findAllForUser(User user) {
        final Criteria criteria = getCurrentSession().createCriteria(Project.class);
        criteria.createCriteria("projectTeam").add(Restrictions.eq("user", user));
        return criteria.list();
    }

}
