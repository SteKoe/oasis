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

package de.stekoe.idss.setup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.log4j.Logger;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.SystemRoleService;
import de.stekoe.idss.service.UserService;

public class DatabaseSetup {

    private static final Logger LOG = Logger.getLogger(DatabaseSetup.class);

    @Inject
    UserService userService;

    @Inject
    SystemRoleService systemRoleService;

    @Inject
    ProjectService projectService;

    @Inject
    AuthService authService;

    private static final List<String> SYSTEMROLES = Arrays.asList(
            SystemRole.USER,
            SystemRole.ADMIN
    );

    private static final List<String> USERNAMES = Arrays.asList(
            "administrator",
            "rainer.zufall",
            "klara.fall",
            "anna.gramm",
            "anna.nass",
            "hans.wurst",
            "jeff.trainer",
            "jo.kher",
            "kai.ser",
            "peer.manent",
            "melita.kaffee",
            "jonas.sprenger",
            "benedikt.ritter",
            "michael.hess",
            "stephan.koeninger"
    );

    private void createSystemRoles() {
        if(systemRoleService.findAll().isEmpty()) {
            for (String systemRole : SYSTEMROLES) {
                SystemRole role = new SystemRole();
                role.setName(systemRole);
                systemRoleService.save(role);
            }
        }
    }

    private void createUsers() {
        for (String name : USERNAMES) {
            User user = new User();
            user.setEmail(name.toLowerCase() + "@example.com");
            user.setUsername(name.toLowerCase());
            user.setPassword(authService.hashPassword("password"));
            user.setActivationKey(null);
            user.setUserStatus(UserStatus.ACTIVATED);

            if (user.getUsername().contains("admin")) {

                final List<SystemRole> systemRoles = new ArrayList<SystemRole>();
                systemRoles.add(systemRoleService.getAdminRole());
                user.setRoles(new HashSet<SystemRole>(systemRoles));
            } else {
                final List<SystemRole> systemRoles = new ArrayList<SystemRole>();
                systemRoles.add(systemRoleService.getUserRole());
                user.setRoles(new HashSet<SystemRole>(systemRoles));
            }

            try {
                userService.save(user);
            } catch (Exception e) {
                LOG.error("Error while saving User!", e);
            }
        }
    }

    private void createSampleProject() {
        Project project = new Project();

        final ProjectRole projectRoleForCreator = createProjectRoleForCreator(project);
        final ProjectRole projectRoleForMember = createProjectRoleForMember(project);

        project.setName("Sample Name for Project");
        project.setDescription("Contrary to popular belief, Lorem Ipsum is not simply random text. <strong>It has roots</strong> in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance.");
        project.getProjectRoles().add(projectRoleForCreator);
        project.getProjectRoles().add(projectRoleForMember);

        ProjectMember projectCreator = new ProjectMember();
        projectCreator.setUser(userService.findByUsername("klara.fall"));
        projectCreator.setProjectRole(projectRoleForCreator);

        project.getProjectTeam().add(projectCreator);

        projectService.save(project);
    }

    private ProjectRole createProjectRoleForCreator(Project project) {
        ProjectRole projectRoleCreator = new ProjectRole();
        projectRoleCreator.setName("Projektleiter");

        final Set<Permission> projectPermissions = new HashSet<Permission>();
        for (PermissionType permissionType : PermissionType.forProject()) {
            projectPermissions.add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }

        projectRoleCreator.setPermissions(projectPermissions);
        return projectRoleCreator;
    }

    private ProjectRole createProjectRoleForMember(Project project) {
        ProjectRole projectRoleMember = new ProjectRole();
        projectRoleMember.setName("Projektmitglied");

        for (PermissionType permissionType : PermissionType.forReadOnly()) {
            projectRoleMember.getPermissions().add(new Permission(PermissionObject.PROJECT, permissionType, project.getId()));
        }
        return projectRoleMember;
    }

    private void installSampleUser() {
        createSystemRoles();
        createUsers();
    }

    private void installSampleProject() {
        createSampleProject();
    }

    public void run() {
        if(userService.findAll().size() == 0) {
            installSampleUser();
            installSampleProject();
        }
    }
}
