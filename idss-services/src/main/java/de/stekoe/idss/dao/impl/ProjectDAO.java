package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.project.Project;
import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class ProjectDAO extends GenericDAO<Project> implements IProjectDAO {

    @Override
    public List<Project> findByProjectName(java.lang.String projectName) {
        Criteria criteria = getSession().createCriteria(Project.class);
        criteria.add(Restrictions.eq("name", projectName));
        return criteria.list();
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

        final Criteria criteria = getSession().createCriteria(Project.class);
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

    @Override
    protected Class<Project> getPersistedClass() {
        return Project.class;
    }
}
