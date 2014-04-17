/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.ProjectStatus;
import de.stekoe.idss.model.User;
import de.stekoe.idss.repository.ProjectRepository;
import de.stekoe.idss.repository.UserRepository;

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
        final Set<Permission> permissions = projectRole.getPermissions();

        // User is allowed to to anything
        if(permissions.contains(PermissionType.ALL)) {
            return true;
        }

        // User has specific roles. So check if he has the one which is necessary to perform action.
        for (Permission permission : permissions) {
            if (permission.getPermissionObject().equals(PermissionObject.valueOf(Project.class)) && permission.getPermissionType().equals(permissionType)) {
                return true;
            }
        }

        return false;
    }

    @Transactional
    public Project save(Project project) {
        return projectRepository.save(project);
    }

    public List<Project> findByUser(String id) {
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

    public List<ProjectStatus> getNextProjectStatus(Project project) {
        List<ProjectStatus> nextProjectStatus = new ArrayList<ProjectStatus>();

        ProjectStatus projectStatus = project.getProjectStatus();
        if(ProjectStatus.EDITING.equals(projectStatus)) {
            nextProjectStatus.add(ProjectStatus.INPROGRESS);
        } else if(ProjectStatus.INPROGRESS.equals(projectStatus)) {
            nextProjectStatus.add(ProjectStatus.FINISHED);
        }

        nextProjectStatus.add(ProjectStatus.CANCELED);
        return nextProjectStatus;

    }
}
