package de.stekoe.idss.page.project;

import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.component.form.project.CreateProjectForm;

public class ProjectCreatePage extends AuthUserPage {
    public ProjectCreatePage() {
        setTitle(getString("form.project.create.title"));
        add(new CreateProjectForm("form.create.project"));
    }
}
