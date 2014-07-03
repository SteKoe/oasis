package de.stekoe.idss.page.project.role;

import java.util.Set;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.project.ProjectPage;

public class ProjectRoleEditPage extends ProjectPage {

    public ProjectRoleEditPage(PageParameters pageParameters) {
        super(pageParameters);
        add(new ProjectRoleForm("projectRoleEditForm", getProjectId(), getProjectRoleIdFromProject(pageParameters)));
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
