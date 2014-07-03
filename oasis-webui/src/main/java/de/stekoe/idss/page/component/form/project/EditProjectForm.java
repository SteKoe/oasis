package de.stekoe.idss.page.component.form.project;

import org.apache.wicket.model.IModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

public class EditProjectForm extends ProjectForm {

    @SpringBean
    private ProjectService projectService;
    @SpringBean
    private ProjectRoleService projectRoleService;

    public EditProjectForm(String id, String projectId) {
        super(id, projectId);
    }

    @Override
    public void onSave(IModel<Project> model) {
        projectService.save(model.getObject());
        WebSession.get().success("Projekt wurde bearbeitet!");
        setResponsePage(getPage().getPageClass(), getPage().getPageParameters());
    }
}
