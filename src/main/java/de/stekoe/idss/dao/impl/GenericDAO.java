package de.stekoe.idss.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.inject.Inject;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAO {

    @Inject
    private SessionFactory sessionFactory;

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
