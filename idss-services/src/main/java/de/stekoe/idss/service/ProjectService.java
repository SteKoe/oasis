/*
 * Copyright 2014 Stephan Köninger
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
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserId;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.project.ProjectRole;
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

    public boolean isAuthorized(final UserId userId, final ProjectId projectId, final PermissionType permissionType) {

        final Project project = projectRepository.findOne(projectId);

        if (authService.isAuthorized(userId, project, permissionType)) {
            return true;
        }

        final User user = userRepository.findOne(userId);

        if (user == null) {
            return false;
        }

        final List<ProjectMember> projectTeam = new ArrayList<ProjectMember>(project.getProjectTeam());
        final ProjectMember pm = (ProjectMember) CollectionUtils.find(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                final ProjectMember projectMember = (ProjectMember) object;
                return projectMember.getUser().equals(user);
            }
        });

        if (pm == null) {
            return false;
        }

        final ProjectRole projectRole = pm.getProjectRole();
        final Set<Permission> permissions = projectRole.getPermissions();

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

    public List<Project> findByUser(UserId id) {
        return projectRepository.findByUser(id);
    }

    public List<Project> findAll() {
        return (List<Project>)projectRepository.findAll();
    }

    public Project findOne(ProjectId id) {
        return projectRepository.findOne(id);
    }

    public List<Project> findAll(Sort pageable) {
        return (List<Project>) projectRepository.findAll(pageable);
    }

    @Transactional
    public void delete(ProjectId id) {
        projectRepository.delete(id);
    }
}
