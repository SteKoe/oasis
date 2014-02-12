package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CriterionPageDAO extends GenericDAO implements ICriterionPageDAO {
    @Override
    public void save(CriterionPage entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(Serializable id) {
        delete(findById(id));
    }

    @Override
    public void delete(CriterionPage entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public CriterionPage findById(Serializable id) {
        return (CriterionPage) getCurrentSession().get(CriterionPage.class, id);
    }

    @Override
    public List<CriterionPage> findAll() {
        return getCurrentSession().createCriteria(CriterionPage.class).list();
    }
}
