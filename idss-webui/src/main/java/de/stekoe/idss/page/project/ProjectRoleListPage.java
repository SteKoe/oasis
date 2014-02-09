package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.ProjectService;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
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
        ArrayList<ProjectRole> projectRoles = new ArrayList<ProjectRole>(getProject().getProjectRoles());
        projectRoles = sortProjectRolesByAmountOfPermissionsDesc(projectRoles);

        final ListView<ProjectRole> projectRolesList = new ListView<ProjectRole>("project.roles.list", projectRoles) {
            @Override
            protected void populateItem(ListItem<ProjectRole> item) {
                final ProjectRole projectRole = item.getModelObject();

                item.add(new Label("project.roles.role.name", projectRole.getName()));

                List<String> permissions = new ArrayList<String>();

                for(Permission p : projectRole.getPermissions()) {
                    final String permissionKey = MessageFormat.format(getString(p.getPermissionType().getKey()), getString("label.project"));
                    permissions.add(permissionKey);
                }
                item.add(new Label("project.roles.role.permissions", StringUtils.join(permissions, ", ")));
            }
        };
        add(projectRolesList);
    }

    private ArrayList<ProjectRole> sortProjectRolesByAmountOfPermissionsDesc(ArrayList<ProjectRole> projectRoles) {
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
