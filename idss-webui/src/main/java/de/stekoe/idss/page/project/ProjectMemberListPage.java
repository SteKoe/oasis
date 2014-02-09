package de.stekoe.idss.page.project;

import de.stekoe.idss.component.form.project.EditProjectMembersForm;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectMemberListPage extends ProjectPage {

    public ProjectMemberListPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new EditProjectMembersForm("form.project.members.edit", getProjectId()));
    }
}
