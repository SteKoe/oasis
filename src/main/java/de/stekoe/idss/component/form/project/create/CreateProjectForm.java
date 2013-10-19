package de.stekoe.idss.component.form.project.create;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.project.ProjectOverviewPage;
import de.stekoe.idss.service.IProjectRoleService;
import de.stekoe.idss.service.IProjectService;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import wicket.contrib.tinymce.TinyMceBehavior;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateProjectForm extends Panel {

    @Inject
    private IProjectService projectService;

    @Inject
    private IProjectRoleService projectRoleService;

    final IModel<Project> projectModel = Model.of(new Project());

    public CreateProjectForm(String id) {
        super(id);
        createProjectForm();
    }

    private void createProjectForm() {
        final Form createProjectForm = new Form("createProjectForm", projectModel) {
            @Override
            protected void onSubmit() {
                final Project project = projectModel.getObject();

                System.out.println(project.getName());

                final User user = WebSession.get().getUser();

                ProjectMember projectLeader = new ProjectMember();
                projectLeader.setUser(user);
                projectLeader.getProjectRoles().add(projectRoleService.getProjectLeaderRole());

                project.getProjectTeam().add(projectLeader);

                projectService.create(project);

                WebSession.get().success("Projekt wurde erstellt!");
                setResponsePage(ProjectOverviewPage.class);
            }
        };
        add(createProjectForm);

        // Project Name
        final ControlGroup projectNameControlGroup = new ControlGroup("projectNameControlGroup");
        final TextField projectName = new TextField("name", new PropertyModel(projectModel, "name"));
        projectNameControlGroup.add(projectName);
        projectName.setLabel(Model.of(getString("project.form.name.label")));
        projectName.add(new PropertyValidator());

        // Project Description
        final ControlGroup projectDescriptionControlGroup = new ControlGroup("projectDescriptionControlGroup");
        final TextArea<String> projectDescription = new TextArea<String>("description", new PropertyModel<String>(projectModel, "description"));
        projectDescriptionControlGroup.add(projectDescription);
        projectDescription.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        projectDescription.add(new PropertyValidator<String>());
        projectDescription.setLabel(Model.of(getString("project.form.description.label")));

        // Buttons
        final ControlGroup buttonsControlGroup = new ControlGroup("buttonControlGroup");
        final Button submitButton = new Button("submitButton");
        buttonsControlGroup.add(submitButton);

        createProjectForm.add(projectNameControlGroup);
        createProjectForm.add(projectDescriptionControlGroup);
        createProjectForm.add(buttonsControlGroup);
    }
}
