package de.stekoe.idss.page.project;

import de.stekoe.idss.page.component.form.project.CreateProjectForm;
import de.stekoe.idss.page.AuthUserPage;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectCreatePage extends AuthUserPage {
    public ProjectCreatePage() {
        add(new CreateProjectForm("form.create.project"));
    }
}
