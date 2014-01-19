package de.stekoe.idss.component.modal;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.*;
import de.stekoe.idss.service.ProjectRoleService;
import de.stekoe.idss.service.UserService;
import org.apache.wicket.markup.html.form.*;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class AddProjectMemberModal extends Modal {

    @SpringBean private UserService userService;
    @SpringBean private ProjectRoleService projectRoleService;

    private ProjectMember projectMember = new ProjectMember();

    public AddProjectMemberModal(java.lang.String markupId) {
        this(markupId, null);
    }

    public AddProjectMemberModal(java.lang.String id, IModel<java.lang.String> model) {
        super(id, model);

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

        final ListMultipleChoice<ProjectRole> projectRoleSelection = new ListMultipleChoice<ProjectRole>("projectRole", new PropertyModel<List<ProjectRole>>(projectMember, "projectRoles"), projectRoleService.getProjectRolesForProject("asd"), new ChoiceRenderer<ProjectRole>("name", "id"));
        form.add(projectRoleSelection);

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
