package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.GenericDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.inject.Inject;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAOImpl implements GenericDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
