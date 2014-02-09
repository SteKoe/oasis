package de.stekoe.idss.page.project.role;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.service.ProjectRoleService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectRoleEditPage extends ProjectPage {
    @SpringBean
    private ProjectRoleService projectRoleService;

    private LoadableDetachableModel<ProjectRole> projectRoleModel;

    public ProjectRoleEditPage(PageParameters pageParameters) {
        super(pageParameters);
        final String projectRoleId = getProjectRoleIdFromProject(pageParameters);
        if(projectRoleId == null) {

        }
        createModel(projectRoleId);

        add(new Label("projectRoleName", getProjectRole().getName()));
        add(new Label("introduction", MessageFormat.format(getString("label.project.role.edit.introduction"), getProjectRole().getName())));

        for(Permission permission : getProjectRole().getPermissions()) {

        }

        final ListView<PermissionType> permissions = new ListView<PermissionType>("roles", new ArrayList(PermissionType.forProject()) {
            @Override
            protected void populateItem(ListItem<PermissionType> item) {
                final PermissionType permissionType = item.getModelObject();

                item.add(new CheckBox("role.checkbox"));
                item.add(new Label("role.label", MessageFormat.format(getString(permissionType.getKey()), getString(permission.getObjectType().toString()))));
            }
        };
        add(permissions);
    }

    private void createModel(final String projectRoleId) {
        projectRoleModel = new LoadableDetachableModel<ProjectRole>() {
            @Override
            protected ProjectRole load() {
                return projectRoleService.findById(projectRoleId);
            }
        };
    }

    private String getProjectRoleIdFromProject(PageParameters pageParameters) {
        final Set<ProjectRole> projectRoles = getProject().getProjectRoles();
        for(ProjectRole projectRole : projectRoles) {
            if(projectRole.getId().equals(pageParameters.get("roleId").toString())) {
                return projectRole.getId();
            }
        }
        return null;
    }

    private ProjectRole getProjectRole() {
        return projectRoleModel.getObject();
    }
}
