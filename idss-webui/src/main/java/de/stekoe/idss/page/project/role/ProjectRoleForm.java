package de.stekoe.idss.page.project.role;

import java.io.Serializable;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBoxMultipleChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.project.ProjectRoleListPage;
import de.stekoe.idss.service.PermissionService;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;

public class ProjectRoleForm extends Panel {

    @Inject
    ProjectRoleService projectRoleService;

    @Inject
    PermissionService permissionService;

    @Inject
    ProjectService projectService;

    private String projectRoleId;
    private LoadableDetachableModel<ProjectRole> projectRoleModel;
    private Form<ProjectRole> projectRoleForm;
    private final List<PermissionType> select = new ArrayList<PermissionType>();

    private String projectId;

    public ProjectRoleForm(final String id, final String projectId) {
        this(id, projectId, null);
    }

    public ProjectRoleForm(final String id, final String projectId, final String projectRoleId) {
        super(id);

        this.projectId = projectId;
        this.projectRoleId = projectRoleId;

        projectRoleModel = new LoadableDetachableModel<ProjectRole>() {
            @Override
            protected ProjectRole load() {
                if(projectRoleId == null) {
                    return new ProjectRole();
                } else {
                    return projectRoleService.findOne(projectRoleId);
                }
            }
        };

        projectRoleForm = new Form<ProjectRole>("projectRoleForm", new CompoundPropertyModel<ProjectRole>(projectRoleModel)) {
            @Override
            protected void onSubmit() {
                final ProjectRole projectRole = getModel().getObject();
                projectRole.getPermissions().clear();

                for (Permission permission : projectRole.getPermissions()) {
                    permissionService.delete(permission);
                }

                // Reassign them
                final Collection<PermissionType> selectedPermissionTypes = getSelectedPermissionTypes();
                for (PermissionType selectedPermissionType : selectedPermissionTypes) {
                    final Permission permission = new Permission(PermissionObject.PROJECT, selectedPermissionType, getProjectId());
                    projectRole.getPermissions().add(permission);
                }
                projectRoleService.save(projectRole);

                Project project = projectService.findOne(getProjectId());
                project.getProjectRoles().remove(projectRole);
                project.getProjectRoles().add(projectRole);
                projectService.save(project);

                getWebSession().success("Role has been saved!");
                setResponsePage(ProjectRoleListPage.class, new PageParameters().add("projectId", getProjectId()));
            }
        };
        add(projectRoleForm);

        projectRoleForm.add(new TextField<String>("name"));

        final CheckBoxMultipleChoice<PermissionType> permissions = new CheckBoxMultipleChoice<PermissionType>("permissionTypes", new Model((Serializable) select), new ArrayList(PermissionType.forProject()), new PermissionTypeChoiceRenderer());
        permissions.setPrefix("<div class='checkbox'>");
        permissions.setSuffix("</div>");
        projectRoleForm.add(permissions);

        for (Permission permission : getProjectRole().getPermissions()) {
            select.add(permission.getPermissionType());
        }

        add(new Label("projectRoleName", getProjectRole().getName()));
    }

    private ProjectRole getProjectRole() {
        return projectRoleModel.getObject();
    }

    protected List<PermissionType> getSelectedPermissionTypes() {
        return select;
    }

    protected String getProjectId() {
        return projectId;
    }

    private class PermissionTypeChoiceRenderer implements IChoiceRenderer<PermissionType> {
        @Override
        public Object getDisplayValue(PermissionType object) {
            return " " + MessageFormat.format(getString(object.getKey()), getString(PermissionObject.PROJECT.getKey()));
        }

        @Override
        public String getIdValue(PermissionType object, int index) {
            return object.getKey();
        }
    };
}
