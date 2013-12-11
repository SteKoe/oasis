package de.stekoe.idss.component.form.project.create;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.project.ProjectOverviewPage;
import de.stekoe.idss.service.IProjectRoleService;
import de.stekoe.idss.service.IProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.util.lang.Bytes;
import wicket.contrib.tinymce.TinyMceBehavior;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateProjectForm extends Panel {

    @Inject
    private IProjectService projectService;

    @Inject
    private IProjectRoleService projectRoleService;

    private final IModel<Project> projectModel = Model.of(new Project());
    private final Collection<FileUpload> uploads = new ArrayList<FileUpload>();

    public CreateProjectForm(String id) {
        super(id);
        createProjectForm();
    }

    private void createProjectForm() {
        final Form createProjectForm = new Form("createProjectForm", projectModel) {

            @Override
            protected void onConfigure() {
                super.onConfigure();

                setMaxSize(Bytes.megabytes(10));
                setMultiPart(true);
            }

            @Override
            protected void onSubmit() {
                final Project project = projectModel.getObject();

                final User user = WebSession.get().getUser();

                ProjectMember projectLeader = new ProjectMember();
                projectLeader.setUser(user);
                projectLeader.getProjectRoles().add(projectRoleService.getProjectLeaderRole());
                projectLeader.setProject(project);

                project.getProjectTeam().add(projectLeader);

                projectService.create(project);

                WebSession.get().success("Projekt wurde erstellt!");
                setResponsePage(ProjectOverviewPage.class);
            }
        };
        add(createProjectForm);

        // Project Name
        final ControlGroup projectNameControlGroup = new ControlGroup("projectNameControlGroup");
        createProjectForm.add(projectNameControlGroup);
        final TextField projectName = new TextField("name", new PropertyModel(projectModel, "name"));
        projectNameControlGroup.add(projectName);
        projectName.setLabel(Model.of(getString("project.form.name.label")));
        projectName.add(new PropertyValidator());

        // Project Description
        final ControlGroup projectDescriptionControlGroup = new ControlGroup("projectDescriptionControlGroup");
        createProjectForm.add(projectDescriptionControlGroup);
        final TextArea<String> projectDescription = new TextArea<String>("description", new PropertyModel<String>(projectModel, "description"));
        projectDescriptionControlGroup.add(projectDescription);
        projectDescription.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        projectDescription.add(new PropertyValidator<String>());
        projectDescription.setLabel(Model.of(getString("project.form.description.label")));

        // File Upload
        final ControlGroup projectFileUploadControlGroup = new ControlGroup("projectFileUploadControlGroup");
        createProjectForm.add(projectFileUploadControlGroup);
        final MultiFileUploadField fileUploadField = new MultiFileUploadField("file", new PropertyModel<Collection<FileUpload>>(this, "uploads"), 5);
        projectFileUploadControlGroup.add(fileUploadField);
        fileUploadField.setLabel(Model.of(getString("project.form.file.label")));

        // Buttons
        final ControlGroup buttonsControlGroup = new ControlGroup("buttonControlGroup");
        createProjectForm.add(buttonsControlGroup);
        final Button submitButton = new Button("submitButton");
        buttonsControlGroup.add(submitButton);
    }

    public Collection<FileUpload> getUploads() {
        return uploads;
    }
}
