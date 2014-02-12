package de.stekoe.idss.dao;

import de.stekoe.idss.model.project.ProjectRole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectRoleDAO extends IGenericDAO<ProjectRole> {
    ProjectRole getRoleByName(String rolename);
}
