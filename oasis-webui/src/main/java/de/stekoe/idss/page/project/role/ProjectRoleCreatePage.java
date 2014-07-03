package de.stekoe.idss.page.project.role;

import java.util.Set;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.project.ProjectPage;

public class ProjectRoleCreatePage extends ProjectPage {

    public ProjectRoleCreatePage(PageParameters pageParameters) {
        super(pageParameters);
        add(new ProjectRoleForm("projectRoleCreateForm", getProjectId()));
    }

    private String getProjectRoleIdFromProject(PageParameters pageParameters) {
        final Set<ProjectRole> projectRoles = getProject().getProjectRoles();
        for (ProjectRole projectRole : projectRoles) {
            if (projectRole.getId().equals(pageParameters.get("roleId").toString())) {
                return projectRole.getId();
            }
        }
        return null;
    }
}
