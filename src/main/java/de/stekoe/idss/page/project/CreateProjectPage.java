package de.stekoe.idss.page.project;

import de.stekoe.idss.component.form.project.create.CreateProjectForm;
import de.stekoe.idss.page.AuthUserPage;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateProjectPage extends AuthUserPage {
    public CreateProjectPage() {
        add(new CreateProjectForm("createProjectForm"));
    }
}
