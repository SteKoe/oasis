package de.stekoe.idss.dao;

import java.util.List;

import de.stekoe.idss.model.Role;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface RoleDAO {

    /**
     * @param entity Update the given User.
     */
    void update(Role role);

    /**
     * @return A List of all roles.
     */
    List<Role> getAllRoles();

    /**
     * @param user A User to be saved in database.
     * @return true on success, false otherwise.
     */
    boolean insert(Role role);

    /**
     * Get a role by its name.
     *
     * @param Rolename The role name to retrieve from database
     * @return The role if found
     */
    Role getRoleByName(String rolename);
}
