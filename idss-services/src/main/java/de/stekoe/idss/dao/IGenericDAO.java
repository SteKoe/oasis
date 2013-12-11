package de.stekoe.idss.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IGenericDAO<T> {
    /**
     * @param entity
     */
    void save(T entity);

    /**
     * @param id
     */
    void delete(Serializable id);

    /**
     * @param entity
     */
    void delete(T entity);

    /**
     * @param id
     * @return
     */
    T findById(Serializable id);

    /**
     * @return
     */
    List<T> findAll();
}
