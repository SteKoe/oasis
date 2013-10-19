package de.stekoe.idss.dao;

import de.stekoe.idss.model.ProjectRole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectRoleDAO extends IGenericDAO<ProjectRole> {
    /**
     * Get a role by its name.
     *
     * @param rolename The role name to retrieve from database
     * @return The role if found
     */
    ProjectRole getRoleByName(String rolename);
}
