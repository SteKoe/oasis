package de.stekoe.idss.service;

import de.stekoe.idss.model.criterion.Criterion;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import de.stekoe.idss.model.criterion.scale.value.OrdinalValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface CriterionService {
    /**
     * @param entity
     */
    void saveCriterion(Criterion entity);

    /**
     * @param id
     * @return
     */
    Criterion findById(String id);

    /**
     * @param criterionId
     */
    void deleteCriterion(String criterionId);

    /**
     * @param value The value to delete from Criterion
     */
    void deleteValue(MeasurementValue value);
}
