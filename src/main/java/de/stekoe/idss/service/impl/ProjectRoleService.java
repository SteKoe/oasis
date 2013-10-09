package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.service.IProjectRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class ProjectRoleService implements IProjectRoleService {

    @Inject
    IProjectRoleDAO projectRoleDAO;

    @Override
    public List<ProjectRole> findAllRoles() {
        return projectRoleDAO.getAllRoles();
    }

    @Override
    public ProjectRole getProjectLeaderRole() {
        return projectRoleDAO.getRoleByName(ProjectRole.LEADER);
    }

    @Override
    public ProjectRole getProjectMemberRole() {
        return projectRoleDAO.getRoleByName(ProjectRole.MEMBER);
    }

}
