package de.stekoe.idss.page.component.modal;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class AddProjectMemberModal extends Modal {

    @SpringBean
    private UserService userService;
    @SpringBean
    private ProjectService projectService;

    private ProjectMember projectMember = new ProjectMember();

    public AddProjectMemberModal(String id, final String projectId) {
        super(id);

        final LoadableDetachableModel<Project> projectModel = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                return projectService.findById(projectId);
            }
        };

        header(Model.of(getString("label.project.add.member")));

        final Form form = new Form("form.add.member") {
            @Override
            protected void onSubmit() {
                super.onSubmit();
                onSave(projectMember);
            }
        };
        add(form);

        final ListChoice<User> userSelection = new ListChoice<User>("user", new PropertyModel<User>(projectMember, "user"), userService.getAllUsers(), new ChoiceRenderer<User>("username", "id"));
        form.add(userSelection);
        userSelection.add(new AttributeModifier("size", 1));

        final ListChoice<ProjectRole> projectRoleSelection = new ListChoice<ProjectRole>("projectRole", new PropertyModel<ProjectRole>(projectMember, "projectRole"), new ArrayList<ProjectRole>(projectModel.getObject().getProjectRoles()), new ChoiceRenderer<ProjectRole>("name", "id"));
        form.add(projectRoleSelection);
        projectRoleSelection.add(new AttributeModifier("size", 1));

        addButton(new ModalCloseButton(new ResourceModel("label.cancel")));

        final SubmitLink submitButton = new SubmitLink("button", form);
        addButton(submitButton);
        submitButton.setBody(Model.of(getString("label.add")));
        submitButton.add(new ButtonBehavior(Buttons.Type.Success));
    }

    public ProjectMember getProjectMember() {
        return projectMember;
    }

    public abstract void onSave(ProjectMember pm);
}
