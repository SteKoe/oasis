package de.stekoe.idss.page.project;

import de.stekoe.idss.component.form.project.EditProjectMembersForm;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectMemberListPage extends ProjectPage {

    private final String projectId;

    public ProjectMemberListPage(PageParameters pageParameters) {
        super(pageParameters);
        this.projectId = pageParameters.get("id").toString();

        add(new EditProjectMembersForm("form.project.members.edit", this.projectId));
    }

    @Override
    protected void onBeforeRender() {
        super.onBeforeRender();
    }
}