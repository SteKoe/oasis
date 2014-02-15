package de.stekoe.idss.service;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface DAOService<T> {
    T findById(String id);
    void save(T entity);
    void delete(T entity);
}
