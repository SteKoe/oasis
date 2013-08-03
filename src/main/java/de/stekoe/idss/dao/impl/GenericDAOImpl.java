package de.stekoe.idss.dao.impl;

import javax.inject.Inject;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import de.stekoe.idss.dao.GenericDAO;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAOImpl implements GenericDAO {

    @Inject
    private SessionFactory sessionFactory;

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
