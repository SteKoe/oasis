package de.stekoe.idss.dao;

import de.stekoe.idss.model.SystemRole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ISystemRoleDAO extends IGenericDAO<SystemRole> {
    /**
     * Get a role by its name.
     *
     * @param rolename The role name to retrieve from database
     * @return The role if found
     */
    SystemRole getRoleByName(String rolename);
}
