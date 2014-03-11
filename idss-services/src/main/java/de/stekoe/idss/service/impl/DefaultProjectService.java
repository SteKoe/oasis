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

package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserId;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.ProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
@Transactional
public class DefaultProjectService implements ProjectService {

    @Autowired
    private IProjectDAO projectDAO;
    @Autowired
    private IUserDAO userDAO;
    @Autowired
    private AuthService authService;

    @Override
    public void delete(ProjectId id) {
        projectDAO.delete(id);
    }

    @Override
    public void save(Project project) {
        projectDAO.save(project);
    }

    @Override
    public Project findById(ProjectId id) {
        return projectDAO.findById(id);
    }

    @Override
    public List<Project> findAllForUser(UserId user) {
        return projectDAO.findAllForUser(user);
    }

    @Override
    public List<Project> findAllForUserPaginated(UserId userId, int perPage, int curPage) {
        return projectDAO.findAllForUserPaginated(userId, perPage, curPage);
    }

    @Override
    public boolean isAuthorized(final UserId userId, final ProjectId projectId, final PermissionType permissionType) {

        final Project project = projectDAO.findById(projectId);

        if (authService.isAuthorized(userId, project, permissionType)) {
            return true;
        }

        final User user = userDAO.findById(userId);

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
}
