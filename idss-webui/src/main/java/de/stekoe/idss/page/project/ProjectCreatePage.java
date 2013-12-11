package de.stekoe.idss.page.project;

import de.stekoe.idss.component.form.project.create.CreateProjectForm;
import de.stekoe.idss.page.AuthUserPage;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectCreatePage extends AuthUserPage {
    public ProjectCreatePage() {
        add(new CreateProjectForm("createProjectForm"));
    }
}
