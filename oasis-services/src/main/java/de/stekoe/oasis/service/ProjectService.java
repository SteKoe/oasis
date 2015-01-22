package de.stekoe.oasis.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.oasis.model.EvaluationStatus;
import de.stekoe.oasis.model.Permission;
import de.stekoe.oasis.model.PermissionObject;
import de.stekoe.oasis.model.PermissionType;
import de.stekoe.oasis.model.Project;
import de.stekoe.oasis.model.ProjectMember;
import de.stekoe.oasis.model.ProjectRole;
import de.stekoe.oasis.model.User;
import de.stekoe.oasis.repository.ProjectRepository;
import de.stekoe.oasis.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class ProjectService {

    @Inject
    private ProjectRepository projectRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthService authService;

    public boolean isAuthorized(final String userId, final String projectId, final PermissionType permissionType) {
        if(userId == null || projectId == null) {
            return false;
        }

        final Project project = projectRepository.findOne(projectId);

        // Has the user any permissions like administrative permissions?
        if (authService.isAuthorized(userId, project, permissionType)) {
            return true;
        }

        // The user to check is not available! No access here!
        final User user = userRepository.findOne(userId);
        if (user == null) {
            return false;
        }

        if(project == null) {
            return false;
        }

        final List<ProjectMember> projectTeam = new ArrayList<ProjectMember>(project.getProjectTeam());
        final ProjectMember pm = (ProjectMember) CollectionUtils.find(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                final ProjectMember projectMember = (ProjectMember) object;
                if(projectMember.getUser() == null) {
                    return false;
                } else {
                    return projectMember.getUser().equals(user);
                }
            }
        });

        // The user isn't even member of the project! No access here!
        if (pm == null) {
            return false;
        }

        final ProjectRole projectRole = pm.getProjectRole();
        if(projectRole == null) {
            return false;
        }
        final Set<Permission> permissions = projectRole.getPermissions();

        // User is allowed to to anything
        if(permissions.contains(PermissionType.ALL)) {
            return true;
        }

        // User has specific roles. So check if he has the one which is necessary to perform action.
        for (Permission permission : permissions) {
            PermissionObject permissionObject = permission.getPermissionObject();
            PermissionObject other = PermissionObject.valueOf(Project.class);
            if (permissionObject.equals(other) && permission.getPermissionType().equals(permissionType)) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> findByUserId(String id) {
        return projectRepository.findByUser(id);
    }

    public List<Project> findAll() {
        return (List<Project>)projectRepository.findAll();
    }

    public Project findOne(String id) {
        return projectRepository.findOne(id);
    }

    public List<Project> findAll(Sort pageable) {
        return (List<Project>) projectRepository.findAll(pageable);
    }

    @Transactional
    public void delete(String id) {
        projectRepository.delete(id);
    }

    /**
     * Returns a list of ProjectStatus (including the current ProjectStatus) which are allowed to set based
     * on the current ProjectStatus. This implements a workflow like behavior.
     *
     * @param project The Project to get next status for
     * @return A list of ProjectStatus
     */
    public List<EvaluationStatus> getNextProjectStatus(Project project) {
        List<EvaluationStatus> nextProjectStatus = new ArrayList<EvaluationStatus>();
        nextProjectStatus.add(project.getProjectStatus());

        EvaluationStatus projectStatus = project.getProjectStatus();
        if(EvaluationStatus.PREPARATION.equals(projectStatus)) {
            nextProjectStatus.add(EvaluationStatus.TESTING);
            nextProjectStatus.add(EvaluationStatus.INPROGRESS);
        } else if(EvaluationStatus.TESTING.equals(projectStatus)) {
            nextProjectStatus.add(EvaluationStatus.PREPARATION);
        } else if(EvaluationStatus.INPROGRESS.equals(projectStatus)) {
            nextProjectStatus.add(EvaluationStatus.FINISHED);
        }

        return nextProjectStatus;
    }

    public Page<Project> findAll(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
}
