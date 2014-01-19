package de.stekoe.idss.service;

import de.stekoe.idss.model.ProjectRole;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
public interface ProjectRoleService {

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

    void save(ProjectRole role);

    List<ProjectRole> getProjectRolesForProject(String projectId);
}
