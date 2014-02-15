package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IScaleDAO;
import de.stekoe.idss.model.scale.Scale;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public class ScaleDAO extends GenericDAO<Scale> implements IScaleDAO {
    @Override
    protected Class<Scale> getPersistedClass() {
        return Scale.class;
    }
}
