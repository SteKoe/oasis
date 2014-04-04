/*
 * Copyright 2014 Stephan Köninger
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

package de.stekoe.idss.page.component.modal;

import java.util.ArrayList;

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

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class AddProjectMemberModal extends Modal {

    @SpringBean
    private UserService userService;
    @SpringBean
    private ProjectService projectService;

    private final ProjectMember projectMember = new ProjectMember();

    public AddProjectMemberModal(String id, final String projectId) {
        super(id);

        final LoadableDetachableModel<Project> projectModel = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                return projectService.findOne(projectId);
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

        final PropertyModel<User> userModel = new PropertyModel<User>(projectMember, "user");
        final ListChoice<User> userSelection = new ListChoice<User>("user", userModel, userService.getAllUsers(), new ChoiceRenderer<User>("username", "id"));
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

    public final ProjectMember getProjectMember() {
        return projectMember;
    }

    public abstract void onSave(ProjectMember pm);
}
