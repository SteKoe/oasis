package de.stekoe.idss.dao;

import de.stekoe.idss.model.SystemRole;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface SystemRoleDAO {

    /**
     * @param role Update the given Role.
     */
    void update(SystemRole role);

    /**
     * @return A List of all roles.
     */
    List<SystemRole> getAllRoles();

    /**
     * @param role A role to be saved in database.
     * @return true on success, false otherwise.
     */
    boolean save(SystemRole role);

    /**
     * Get a role by its name.
     *
     * @param rolename The role name to retrieve from database
     * @return The role if found
     */
    SystemRole getRoleByName(String rolename);
}
