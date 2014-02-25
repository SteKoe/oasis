package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.ICriterionDAO;
import de.stekoe.idss.model.criterion.Criterion;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class CriterionDAO extends GenericDAO<Criterion> implements ICriterionDAO {
    @Override
    protected Class<Criterion> getPersistedClass() {
        return Criterion.class;
    }
}
