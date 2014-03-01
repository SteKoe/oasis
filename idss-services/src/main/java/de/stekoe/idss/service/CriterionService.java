package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.Criterion;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionService {

    /**
     * @param entity
     */
    void save(Criterion entity);

    /**
     * @param id
     * @return
     */
    Criterion findById(String id);

    /**
     * @param aCriterion
     */
    void findPageOfCriterionElement(SingleScaledCriterion aCriterion);
}
