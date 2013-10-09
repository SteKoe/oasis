package de.stekoe.idss.dao;

import de.stekoe.idss.model.ProjectRole;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectRoleDAO {
    /**
     * @param role Update the given Role.
     */
    void update(ProjectRole role);

    /**
     * @return A List of all roles.
     */
    List<ProjectRole> getAllRoles();

    /**
     * @param role A role to be saved in database.
     * @return true on success, false otherwise.
     */
    boolean save(ProjectRole role);

    /**
     * Get a role by its name.
     *
     * @param rolename The role name to retrieve from database
     * @return The role if found
     */
    ProjectRole getRoleByName(String rolename);
}
