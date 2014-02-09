package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.project.Project;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Transactional
public class ProjectDAO extends GenericDAO implements IProjectDAO {

    @Override
    public void save(Project project) {
        getCurrentSession().saveOrUpdate(project);
    }

    @Override
    public void delete(Serializable id) {
        final Project project = findById(id);
        delete(project);
    }

    @Override
    public List<Project> findByProjectName(java.lang.String projectName) {
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
        getCurrentSession().delete(entity);
    }

    @Override
    public List<Project> findAll() {
        return getCurrentSession().createCriteria(Project.class).list();
    }

    @Override
    public List<Project> findAllForUser(String user) {
        final Criteria criteria = getAllProjectsForUser(user);
        return criteria.list();
    }

    private Criteria getAllProjectsForUser(String userId) {
        DetachedCriteria idsOnlyCriteria = DetachedCriteria.forClass(Project.class);
        idsOnlyCriteria.createCriteria("projectTeam").add(Restrictions.eq("user.id", userId));
        idsOnlyCriteria.setProjection(Projections.distinct(Projections.id()));

        final Criteria criteria = getCurrentSession().createCriteria(Project.class);
        criteria.add(Subqueries.propertyIn("id", idsOnlyCriteria));
        return criteria;
    }

    @Override
    public List<Project> findAllForUserPaginated(String userId, int perPage, int curPage) {
        final Criteria criteria = getAllProjectsForUser(userId);
        criteria.setFirstResult(perPage * curPage);
        criteria.setMaxResults(perPage);
        return criteria.list();
    }

}
