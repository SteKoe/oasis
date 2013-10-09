package de.stekoe.idss.dao;

import org.hibernate.Session;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IGenericDAO {

    /**
     * @return The current Hibernate Session.
     */
    Session getCurrentSession();
}
