/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
import org.apache.wicket.spring.injection.annot.SpringBean;

import wicket.contrib.tinymce.TinyMceBehavior;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectStatus;
import de.stekoe.idss.page.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.page.project.ProjectListPage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.wicket.EnumChoiceRenderer;

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
        final DropDownChoice<ProjectStatus> projectStatus = new DropDownChoice<ProjectStatus>("projectStatus", new ArrayList<ProjectStatus>(projectService.getNextProjectStatus(projectForm.getModel().getObject())), new EnumChoiceRenderer<ProjectStatus>());
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
