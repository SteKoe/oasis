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

package de.stekoe.idss.page.project.role;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.model.project.ProjectRoleId;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.page.project.ProjectRoleListPage;
import de.stekoe.idss.service.PermissionService;
import de.stekoe.idss.service.ProjectRoleService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectRoleEditPage extends ProjectPage {
    @SpringBean
    private ProjectRoleService projectRoleService;

    @SpringBean
    private PermissionService permissionService;

    private String name;

    private LoadableDetachableModel<ProjectRole> projectRoleModel;
    private ArrayList<PermissionType> select = new ArrayList<PermissionType>();

    public ProjectRoleEditPage(PageParameters pageParameters) {
        super(pageParameters);
        final ProjectRoleId projectRoleId = getProjectRoleIdFromProject(pageParameters);
        if (projectRoleId == null) {

        }
        createModel(projectRoleId);

        add(new Label("projectRoleName", getProjectRole().getName()));
        add(new Label("introduction", MessageFormat.format(getString("label.project.role.edit.introduction"), getProjectRole().getName(), getProject().getName())));

        for (Permission permission : getProjectRole().getPermissions()) {
            select.add(permission.getPermissionType());
        }


        final IChoiceRenderer<PermissionType> iChoiceRenderer = new IChoiceRenderer<PermissionType>() {
            @Override
            public Object getDisplayValue(PermissionType object) {
                return " " + MessageFormat.format(getString(object.getKey()), getString(PermissionObject.PROJECT.getKey()));
            }

            @Override
            public String getIdValue(PermissionType object, int index) {
                return object.getKey();
            }
        };


        final CheckBoxMultipleChoice<PermissionType> permissions = new CheckBoxMultipleChoice<PermissionType>("permissionTypes", new Model(select), new ArrayList(PermissionType.forProject()), iChoiceRenderer);
        permissions.setPrefix("<div class='checkbox'>");
        permissions.setSuffix("</div>");

        Form form = new Form("permissionEditForm", new CompoundPropertyModel(getProjectRole())) {
            @Override
            protected void onSubmit() {
                // Delete all permissions

                final ProjectRole projectRole = getProjectRole();
                projectRole.getPermissions().clear();

                for (Permission permission : projectRole.getPermissions()) {
                    permissionService.delete(permission);
                }

                // Reassign them
                final Collection<PermissionType> selectedPermissionTypes = permissions.getModelObject();
                for (PermissionType selectedPermissionType : selectedPermissionTypes) {
                    final Permission permission = new Permission(PermissionObject.PROJECT, selectedPermissionType, getProjectId());
                    projectRole.getPermissions().add(permission);
                }
                projectRoleService.save(projectRole);

                getWebSession().success("Role has been saved!");
                setResponsePage(ProjectRoleListPage.class, new PageParameters().add("id", getProjectId()));
            }
        };
        add(form);
        form.add(permissions);
        form.add(new TextField<String>("name"));
    }

    private void createModel(final ProjectRoleId projectRoleId) {
        projectRoleModel = new LoadableDetachableModel<ProjectRole>() {
            @Override
            protected ProjectRole load() {
                return projectRoleService.findById(projectRoleId);
            }
        };
    }

    private ProjectRoleId getProjectRoleIdFromProject(PageParameters pageParameters) {
        final Set<ProjectRole> projectRoles = getProject().getProjectRoles();
        for (ProjectRole projectRole : projectRoles) {
            if (projectRole.getId().equals(pageParameters.get("roleId").toString())) {
                return projectRole.getId();
            }
        }
        return null;
    }

    private ProjectRole getProjectRole() {
        return projectRoleModel.getObject();
    }
}
