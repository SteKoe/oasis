package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.impl.CriterionDAO;
import de.stekoe.idss.model.criterion.Criterion;
import de.stekoe.idss.service.CriterionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultCriterionService implements CriterionService {

    @Inject
    private CriterionDAO itsCriterionDAO;

    @Override
    public Criterion findById(String id) {
        return itsCriterionDAO.findById(id);
    }

    @Override
    public void save(Criterion entity) {
        itsCriterionDAO.save(entity);
    }

    @Override
    public void delete(Criterion entity) {
        itsCriterionDAO.delete(entity);
    }
}
