package de.stekoe.idss.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IGenericDAO<T> {
    /**
     * Save the given entity to database
     *
     * @param entity
     */
    void save(T entity);

    /**
     * Delete the entity which is identified by the given id
     *
     * @param id
     */
    void delete(Serializable id);

    /**
     * Delete the given entity from database
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Find an entity by the given id
     *
     * @param id The id to lookup
     * @return The entity object or null
     */
    T findById(Serializable id);

    /**
     * Find all entities of the given type
     *
     * @return a list of entitites
     */
    List<T> findAll();
}
