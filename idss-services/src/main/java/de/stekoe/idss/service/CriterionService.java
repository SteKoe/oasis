package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.Criterion;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionService {
    void save(Criterion entity);
    Criterion findById(String id);
    void delete(Criterion entity);
}
