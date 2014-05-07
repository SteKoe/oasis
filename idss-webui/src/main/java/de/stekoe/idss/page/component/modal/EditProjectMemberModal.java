package de.stekoe.idss.page.component.modal;

import javax.inject.Inject;

import org.apache.wicket.model.IModel;

import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.service.ProjectMemberService;

public class EditProjectMemberModal extends ProjectMemberModal {

    public EditProjectMemberModal(String wicketId, String projectId, String projectMemberId) {
        super(wicketId, projectId, projectMemberId);
    }

    @Inject
    ProjectMemberService projectMemberService;

    @Override
    public void onSave(IModel<ProjectMember> projectMemberModel) {
        projectMemberService.save(projectMemberModel.getObject());

        setResponsePage(getPage().getClass(), getPage().getPageParameters());
    }
}
