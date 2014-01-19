package de.stekoe.idss.component.form.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class EditProjectForm extends ProjectForm {

    @SpringBean private ProjectService projectService;
    @SpringBean private ProjectRoleService projectRoleService;

    public EditProjectForm(String id, String projectId) {
        super(id, projectId);
    }

    @Override
    public void onSave(IModel<Project> model) {
        WebSession.get().success("Projekt wurde bearbeitet!");
        setResponsePage(ProjectListPage.class);
    }
}
