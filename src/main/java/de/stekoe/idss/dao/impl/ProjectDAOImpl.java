package de.stekoe.idss.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import de.stekoe.idss.dao.ProjectDAO;
import de.stekoe.idss.model.Project;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
public class ProjectDAOImpl extends GenericDAOImpl implements ProjectDAO {

    @Override
    public boolean save(Project project) {
        getCurrentSession().saveOrUpdate(project);
        return false;
    }

    @Override
    public List<Project> findByProjectName(String projectName) {
        Criteria criteria = getCurrentSession().createCriteria(Project.class);
        criteria.add(Restrictions.eq("projectName", projectName));
        return criteria.list();
    }

    @Override
    public Project findById(String id) {
        return (Project) getCurrentSession().get(Project.class, id);
    }

}
