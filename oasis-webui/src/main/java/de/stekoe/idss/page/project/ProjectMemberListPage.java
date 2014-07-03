package de.stekoe.idss.page.project;

import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.page.component.form.project.EditProjectMembersForm;

public class ProjectMemberListPage extends ProjectPage {

    public ProjectMemberListPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new EditProjectMembersForm("form.project.members.edit", getProjectId()));
    }
}
