package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.service.ProjectRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultProjectRoleService implements ProjectRoleService {

    @Autowired
    IProjectRoleDAO projectRoleDAO;

    @Override
    public List<ProjectRole> findAllRoles() {
        return projectRoleDAO.findAll();
    }

    @Override
    public ProjectRole getProjectLeaderRole() {
        return projectRoleDAO.getRoleByName(ProjectRole.LEADER_CONSTANT);
    }

    @Override
    public ProjectRole getProjectMemberRole() {
        return projectRoleDAO.getRoleByName(ProjectRole.MEMBER_CONSTANT);
    }

    @Override
    public void save(ProjectRole role) {
        projectRoleDAO.save(role);
    }

    @Override
    public List<ProjectRole> getProjectRolesForProject(String projectId) {
        return projectRoleDAO.findAll();
    }

}
