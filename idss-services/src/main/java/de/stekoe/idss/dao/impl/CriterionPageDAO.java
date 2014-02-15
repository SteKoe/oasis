package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class CriterionPageDAO extends GenericDAO<CriterionPage> implements ICriterionPageDAO {

    @Override
    public List<CriterionPage> findAllForProject(String projectId) {
        final Criteria c = getSession().createCriteria(getPersistedClass());
        c.add(Restrictions.eq("project.id", projectId));
        c.addOrder(Order.asc("ordering"));
        return c.list();
    }

    @Override
    public int getNextPageNumForProject(String projectId) {
        final Criteria c = getSession().createCriteria(getPersistedClass());
        c.add(Restrictions.eq("project.id", projectId));
        c.setProjection(Projections.max("ordering"));

        final Object o = c.uniqueResult();
        if(o == null)
            return 1;

        return (int)o + 1;
    }

    @Override
    protected Class<CriterionPage> getPersistedClass() {
        return CriterionPage.class;
    }
}
