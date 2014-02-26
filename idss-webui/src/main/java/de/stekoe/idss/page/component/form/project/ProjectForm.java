package de.stekoe.idss.page.component.form.project;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.stekoe.idss.model.enums.ProjectStatus;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.wicket.EnumChoiceRenderer;
import org.apache.wicket.Component;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import wicket.contrib.tinymce.TinyMceBehavior;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class ProjectForm extends Panel {

    @SpringBean
    private ProjectService projectService;

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
                if (projectId == null) {
                    return new Project();
                }

                return projectService.findById(projectId);
            }
        };

        Form<Project> projectForm = new Form<Project>("form.project", new CompoundPropertyModel<Project>(projectModel)) {
            @Override
            protected void onSubmit() {
                onSave(getModel());
            }
        };

        addProjectNameField(projectForm);
        addProjectDescriptionField(projectForm);
        addProjectStatusField(projectForm);
        addProjectStartDateField(projectForm);
        addProjectEndDateField(projectForm);
        addButtons(projectForm);

        return projectForm;
    }

    private void addProjectStartDateField(Form<Project> projectForm) {
        Locale.setDefault(getWebSession().getLocale());
        final DateTextField projectStartDate = new DateTextField("projectStartDate");
        projectForm.add(projectStartDate);
    }

    private void addProjectEndDateField(Form<Project> projectForm) {
        final TextField projectEndDate = new TextField("projectEndDate");
        projectForm.add(projectEndDate);
    }

    private void addButtons(Form<Project> projectForm) {
        final Button submitButton = new Button("submitButton");
        projectForm.add(submitButton);

        final BookmarkablePageLink<ProjectListPage> cancelButton = new BookmarkablePageLink<ProjectListPage>("cancelButton", ProjectListPage.class);
        projectForm.add(cancelButton);
    }

    private void addProjectStatusField(Form<Project> projectForm) {
        final DropDownChoice<ProjectStatus> projectStatus = new DropDownChoice<ProjectStatus>("projectStatus", new ArrayList<ProjectStatus>(Arrays.asList(ProjectStatus.values())), new EnumChoiceRenderer<ProjectStatus>());
        projectForm.add(projectStatus);
    }

    private void addProjectNameField(Form<Project> projectForm) {
        final TextField projectName = new TextField("name");
        projectForm.add(projectName);
        projectName.add(new PropertyValidator());
    }

    private void addProjectDescriptionField(Form<Project> projectForm) {
        final TextArea<String> projectDescription = new TextArea<String>("description");
        projectForm.add(projectDescription);
        projectDescription.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        projectDescription.add(new PropertyValidator<String>());
    }

    public abstract void onSave(IModel<Project> model);
}
