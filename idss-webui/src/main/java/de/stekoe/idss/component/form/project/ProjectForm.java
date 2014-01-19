package de.stekoe.idss.component.form.project;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.ProjectService;
import org.apache.wicket.Component;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.MultiFileUploadField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.tinymce.TinyMceBehavior;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class ProjectForm extends Panel {

    @SpringBean private ProjectService projectService;

    private final String projectId;

    public ProjectForm(String id) {
        this(id, null);
    }

    public ProjectForm(String id, String projectId) {
        super(id);
        this.projectId = projectId;

        add(createProjectForm());
    }

    private Component createProjectForm() {
        final LoadableDetachableModel<Project> projectModel = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                if(projectId == null)
                    return new Project();

                return projectService.findById(projectId);
            }
        };

        Form<Project> projectForm = new Form<Project >("form.project", new CompoundPropertyModel<Project>(projectModel)) {
            @Override
            protected void onConfigure() {
                super.onConfigure();

                // Project Name
                final ControlGroup projectNameControlGroup = new ControlGroup("projectNameControlGroup", Model.of(getString("form.project.name.label")));
                add(projectNameControlGroup);
                final TextField projectName = new TextField("name");
                projectNameControlGroup.add(projectName);
                projectName.add(new PropertyValidator());

                // Project Description
                final ControlGroup projectDescriptionControlGroup = new ControlGroup("projectDescriptionControlGroup", Model.of(getString("form.project.description.label")));
                add(projectDescriptionControlGroup);

                final TextArea<String> projectDescription = new TextArea<String>("description");
                projectDescriptionControlGroup.add(projectDescription);
                projectDescription.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
                projectDescription.add(new PropertyValidator<String>());

                // File Upload
                final ControlGroup projectFileUploadControlGroup = new ControlGroup("projectFileUploadControlGroup", Model.of(getString("form.project.file.label")));
                add(projectFileUploadControlGroup);
                final MultiFileUploadField fileUploadField = new MultiFileUploadField("files", 5);
                projectFileUploadControlGroup.add(fileUploadField);

                // Buttons
                final ControlGroup buttonsControlGroup = new ControlGroup("buttonControlGroup");
                add(buttonsControlGroup);

                final Button submitButton = new Button("submitButton");
                buttonsControlGroup.add(submitButton);

                final BookmarkablePageLink<ProjectListPage> cancelButton = new BookmarkablePageLink<ProjectListPage>("cancelButton", ProjectListPage.class);
                buttonsControlGroup.add(cancelButton);
            }

            @Override
            protected void onSubmit() {
                onSave(getModel());
            }
        };

        return projectForm;
    }

    public abstract void onSave(IModel<Project> model);
}
