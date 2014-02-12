package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IChoiceDAO;
import de.stekoe.idss.model.scale.Choice;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class ChoiceDAO extends GenericDAO<Choice> implements IChoiceDAO {
    @Override
    protected Class getPersistedClass() {
        return Choice.class;
    }
}
