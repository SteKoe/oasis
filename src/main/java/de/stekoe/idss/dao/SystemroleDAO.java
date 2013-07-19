package de.stekoe.idss.dao;

import de.stekoe.idss.model.Systemrole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface SystemroleDAO {
    /**
     * @param systemrole The Systemrole which should be saved.
     */
    void saveOrUpdate(Systemrole systemrole);
}
