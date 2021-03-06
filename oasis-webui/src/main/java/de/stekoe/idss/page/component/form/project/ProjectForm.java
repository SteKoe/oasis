package de.stekoe.idss.page.component.form.project;

import java.util.ArrayList;
import java.util.Locale;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Component;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.extensions.markup.html.form.DateTextField;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.tinymce.TinyMceBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.FormGroup;
import de.stekoe.idss.model.EvaluationStatus;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.wicket.EnumChoiceRenderer;
import de.stekoe.idss.wicket.MarkRequiredFieldsBehavior;

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

                return projectService.findOne(projectId);
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
//        addProjectStartDateField(projectForm);
//        addProjectEndDateField(projectForm);
        addButtons(projectForm);

        projectForm.add(new MarkRequiredFieldsBehavior());
        return projectForm;
    }

    private void addProjectStartDateField(Form<Project> projectForm) {
        Locale.setDefault(getWebSession().getLocale());

        WebMarkupContainer webComponent = new WebMarkupContainer("projectStartDatePicker");
        projectForm.add(webComponent);
        webComponent.add(new AttributeModifier("data-date", projectForm.getModelObject().getProjectStartDate()));
        webComponent.add(new AttributeModifier("data-date-format", getString("date.format")));

        final DateTextField projectStartDate = new DateTextField("projectStartDate", getString("date.format"));
        webComponent.add(projectStartDate);
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
        final DropDownChoice<EvaluationStatus> projectStatus = new DropDownChoice<EvaluationStatus>("projectStatus", new ArrayList<EvaluationStatus>(projectService.getNextProjectStatus(projectForm.getModel().getObject())), new EnumChoiceRenderer<EvaluationStatus>());
        projectStatus.setLabel(new Model(getString("label.project.status")));
        projectForm.add(new FormGroup("group.projectStatus").add(projectStatus));
    }

    private void addProjectNameField(Form<Project> projectForm) {
        final TextField projectName = new TextField("name");
        projectName.setLabel(new Model(getString("label.name")));
        projectName.add(new PropertyValidator());
        projectForm.add(new FormGroup("group.name").add(projectName));
    }

    private void addProjectDescriptionField(Form<Project> projectForm) {
        final TextArea<String> projectDescription = new TextArea<String>("description");
        projectDescription.setLabel(new Model(getString("label.description")));
        projectDescription.add(new TinyMceBehavior(CustomTinyMCESettings.getStandard()));
        projectDescription.add(new PropertyValidator<String>());
        projectForm.add(new FormGroup("group.description").add(projectDescription));
    }

    public abstract void onSave(IModel<Project> model);
}
