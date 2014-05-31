package de.stekoe.idss.page.component.modal;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.service.ProjectService;

public class AddProjectMemberModal extends ProjectMemberModal {

    public AddProjectMemberModal(String wicketId, String projectId) {
        super(wicketId, projectId);
    }

    @Inject
    ProjectService projectService;

    @Override
    public void onSave(IModel<ProjectMember> projectMemberModel) {
        ProjectMember projectMember = projectMemberModel.getObject();
        if (projectMember.getUser() != null) {
            Project project = getProject();
            project.getProjectTeam().add(projectMember);
            projectService.save(project);
        }

        setResponsePage(getPage().getClass(), getPage().getPageParameters());
    }
}
