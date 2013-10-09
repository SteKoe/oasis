package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IGenericDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public abstract class GenericDAO implements IGenericDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

}
