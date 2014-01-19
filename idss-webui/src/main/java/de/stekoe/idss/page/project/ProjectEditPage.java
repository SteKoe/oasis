package de.stekoe.idss.page.project;

import de.stekoe.idss.component.form.project.EditProjectForm;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.ProjectService;
import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectEditPage extends AuthUserPage {

    private final String projectId;
    @SpringBean private ProjectService projectService;

    private static final Logger LOG = Logger.getLogger(ProjectEditPage.class);

    public ProjectEditPage(PageParameters params) {
        super(params);

        this.projectId = params.get("id").toString();

        add(new EditProjectForm("form.project.edit", this.projectId));
    }
}
