package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ICriterionPageDAO;
import de.stekoe.idss.model.criterion.CriterionPage;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class CriterionPageDAO extends GenericDAO<CriterionPage> implements ICriterionPageDAO {
    @Override
    protected Class getPersistedClass() {
        return CriterionPage.class;
    }
}
