package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.ProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
@Transactional
public class DefaultProjectService implements ProjectService {

    @Autowired private IProjectDAO projectDAO;
    @Autowired private IUserDAO userDAO;
    @Autowired private AuthService authService;

    @Override
    public void delete(java.lang.String id) {
        projectDAO.delete(id);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public Project findById(java.lang.String id) {
        return projectDAO.findById(id);
    }

    @Override
    public List<Project> findAllForUser(String user) {
        return projectDAO.findAllForUser(user);
    }

    @Override
    public List<Project> findAllForUserPaginated(String userId, int perPage, int curPage) {
        return projectDAO.findAllForUserPaginated(userId, perPage, curPage);
    }

    @Override
    public boolean isAuthorized(final String userId, final String projectId, final PermissionType permissionType) {

        final Project project = projectDAO.findById(projectId);

        if(authService.isAuthorized(userId, project, permissionType)) {
            return true;
        }

        final User user = userDAO.findById(userId);

        if(user == null) {
            return false;
        }

        final List<ProjectMember> projectTeam = new ArrayList<ProjectMember>(project.getProjectTeam());
        final ProjectMember pm = (ProjectMember)CollectionUtils.find(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                return ((ProjectMember) object).getUser().equals(user);
            }
        });

        if(pm == null) {
            return false;
        }

        final Set<Permission> permissions = pm.getProjectRole().getPermissions();

        for(Permission permission : permissions) {
            if(permission.getObjectType().equals(PermissionObject.valueOf(Project.class)) && permission.getPermissionType().equals(permissionType)) {
                return true;
            }
        }

        return false;
    }
}
