package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.impl.CriterionDAO;
import de.stekoe.idss.dao.impl.CriterionPageDAO;
import de.stekoe.idss.model.criterion.Criterion;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.model.criterion.scale.Scale;
import de.stekoe.idss.model.criterion.scale.value.MeasurementValue;
import de.stekoe.idss.service.CriterionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.Iterator;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultCriterionService implements CriterionService {

    @Inject
    private CriterionDAO itsCriterionDAO;

    @Inject
    private CriterionPageDAO itsCriterionPageDAO;

    @Override
    public Criterion findById(String id) {
        return itsCriterionDAO.findById(id);
    }

    @Override
    public void saveCriterion(Criterion entity) {
        itsCriterionDAO.save(entity);
    }

    @Override
    public void deleteCriterion(String criterionId) {
        itsCriterionDAO.delete(criterionId);
    }

    @Override
    public void deleteValue(MeasurementValue value) {
        final Scale scale = value.getScale();
        scale.getValues().remove(value);

        reorderValues(scale.getValues());

        final SingleScaledCriterion criterion = scale.getCriterion();
        saveCriterion(criterion);
    }

    private void reorderValues(List<MeasurementValue> values) {
        int index = 1;
        final Iterator<MeasurementValue> iterator = values.iterator();
        while(iterator.hasNext()) {
            iterator.next().setOrdering(index++);
        }
    }
}
