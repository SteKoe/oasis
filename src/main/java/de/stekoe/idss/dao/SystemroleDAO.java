package de.stekoe.idss.dao;

import de.stekoe.idss.model.Systemrole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface SystemroleDAO {
    void saveOrUpdate(Systemrole systemrole);
}
