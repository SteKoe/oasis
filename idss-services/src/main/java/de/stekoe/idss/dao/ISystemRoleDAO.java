package de.stekoe.idss.dao;

import de.stekoe.idss.model.SystemRole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ISystemRoleDAO extends IGenericDAO<SystemRole> {
    SystemRole getRoleByName(String rolename);
}
