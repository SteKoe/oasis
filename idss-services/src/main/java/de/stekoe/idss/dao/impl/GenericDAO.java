package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IGenericDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAO<T> implements IGenericDAO<T> {

    @Autowired private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(Serializable id) {
        delete(findById(id));
    }

    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    @Override
    public T findById(Serializable id) {
        return (T) getCurrentSession().get(getPersistedClass(), id);
    }

    @Override
    public List<T> findAll() {
        return getCurrentSession().createCriteria(getPersistedClass()).list();
    }

    protected abstract Class getPersistedClass();
}
