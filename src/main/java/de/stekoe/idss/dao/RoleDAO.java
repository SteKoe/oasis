package de.stekoe.idss.dao;

import java.util.List;

import de.stekoe.idss.model.Role;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface RoleDAO {

    /**
     * @param role Update the given Role.
     */
    void update(Role role);

    /**
     * @return A List of all roles.
     */
    List<Role> getAllRoles();

    /**
     * @param role A role to be saved in database.
     * @return true on success, false otherwise.
     */
    boolean insert(Role role);

    /**
     * Get a role by its name.
     *
     * @param rolename The role name to retrieve from database
     * @return The role if found
     */
    Role getRoleByName(String rolename);
}
