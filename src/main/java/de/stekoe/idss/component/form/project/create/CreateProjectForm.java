package de.stekoe.idss.component.form.project.create;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.ControlGroup;
import de.stekoe.idss.model.Project;
import org.apache.wicket.bean.validation.PropertyValidator;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CreateProjectForm extends Panel {

    final IModel<Project> projectModel = Model.of(new Project());

    public CreateProjectForm(String id) {
        super(id);
        createProjectForm();
    }

    private void createProjectForm() {
        final Form createProjectForm = new Form("createProjectForm", projectModel) {
            @Override
            protected void onSubmit() {
                if(hasError()) {
                    System.out.println("Form ahs errors");
                } else {
                    System.out.println("Form has no errors");
                }
            }
        };
        add(createProjectForm);

        // Project Name
        final ControlGroup projectNameControlGroup = new ControlGroup("projectNameControlGroup");
        final TextField projectName = new TextField("name", new PropertyModel(projectModel, "name"));
        projectName.setLabel(Model.of(getString("project.form.name.label")));
        projectName.add(new PropertyValidator());
        projectNameControlGroup.add(projectName);

        // Project Description
        final ControlGroup projectDescriptionControlGroup = new ControlGroup("projectDescriptionControlGroup");
        final TextArea<String> projectDescription = new TextArea<String>("description", new PropertyModel<String>(projectModel, "description"));
        projectDescription.add(new PropertyValidator<String>());
        projectDescription.setLabel(Model.of(getString("project.form.description.label")));
        projectDescriptionControlGroup.add(projectDescription);

        // Buttons
        final ControlGroup buttonsControlGroup = new ControlGroup("buttonControlGroup");
        final Button submitButton = new Button("submitButton");
        buttonsControlGroup.add(submitButton);

        createProjectForm.add(projectNameControlGroup);
        createProjectForm.add(projectDescriptionControlGroup);
        createProjectForm.add(buttonsControlGroup);
    }
}
