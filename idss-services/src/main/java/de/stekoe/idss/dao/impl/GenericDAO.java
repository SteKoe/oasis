package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IGenericDAO;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAO<T> implements IGenericDAO<T> {

    private static final Logger LOG = Logger.getLogger(GenericDAO.class);

    @Autowired private SessionFactory sessionFactory;

    public Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(T entity) {
        try {
            getSession().merge(entity);
            getSession().flush();
            getSession().clear();
        } catch (HibernateException e) {
            LOG.error("Error during saving entity " + entity, e);
        }
    }

    @Override
    public void delete(Serializable id) {
        delete(findById(id));
    }

    @Override
    public void delete(T entity) {
        getSession().delete(entity);
        getSession().flush();
        getSession().clear();
    }

    @Override
    public T findById(Serializable id) {
        return (T) getSession().get(getPersistedClass(), id);
    }

    @Override
    public List<T> findAll() {
        return getSession().createCriteria(getPersistedClass()).list();
    }

    protected abstract Class<? extends Serializable> getPersistedClass();
}
