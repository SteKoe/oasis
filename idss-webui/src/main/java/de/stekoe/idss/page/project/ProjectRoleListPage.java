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

package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.project.role.ProjectRoleEditPage;
import de.stekoe.idss.service.ProjectService;

import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectRoleListPage extends ProjectPage {

    @SpringBean
    private ProjectService projectService;

    public ProjectRoleListPage(PageParameters pageParameters) {
        super(pageParameters);

        addProjectRolesList();
    }

    private void addProjectRolesList() {
        List<ProjectRole> projectRoles = new ArrayList<ProjectRole>(getProject().getProjectRoles());
        projectRoles = sortProjectRolesByAmountOfPermissionsDesc(projectRoles);

        final ListView<ProjectRole> projectRolesList = new ListView<ProjectRole>("project.roles.list", projectRoles) {
            @Override
            protected void populateItem(ListItem<ProjectRole> item) {
                final ProjectRole projectRole = item.getModelObject();

                item.add(new Label("project.roles.role.name", projectRole.getName()));

                List<String> permissions = new ArrayList<String>();
                for (Permission p : projectRole.getPermissions()) {
                    final String permissionKey = MessageFormat.format(getString(p.getPermissionType().getKey()), getString("label.project"));
                    permissions.add(permissionKey);
                }
                item.add(new Label("project.roles.role.permissions", StringUtils.join(permissions, ", ")));

                item.add(new BookmarkablePageLink<ProjectRoleEditPage>("project.role.link.edit", ProjectRoleEditPage.class, new PageParameters(getPageParameters()).add("roleId", projectRole.getId())));
            }
        };
        add(projectRolesList);
    }

    private List<ProjectRole> sortProjectRolesByAmountOfPermissionsDesc(List<ProjectRole> projectRoles) {
        Collections.sort(projectRoles, new Comparator<ProjectRole>() {
            @Override
            public int compare(ProjectRole role1, ProjectRole role2) {
                final int role1Permissions = role1.getPermissions().size();
                final int role2Permissions = role2.getPermissions().size();

                if (role1Permissions > role2Permissions) {
                    return -1;
                } else if (role1Permissions < role2Permissions) {
                    return 1;
                } else {
                    return 0;
                }
            }
        });
        return projectRoles;
    }
}
