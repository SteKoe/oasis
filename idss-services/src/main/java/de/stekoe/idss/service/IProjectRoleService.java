package de.stekoe.idss.service;

import de.stekoe.idss.model.ProjectRole;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface IProjectRoleService {

    /**
     * Get all system roles available in the system.
     * @return a list of system roles
     */
    List<ProjectRole> findAllRoles();

    /**
     * @return the Project Leader Role
     */
    ProjectRole getProjectLeaderRole();

    /**
     * @return the Project Member Role
     */
    ProjectRole getProjectMemberRole();
}
