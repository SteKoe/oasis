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

package de.stekoe.idss.page.component.modal;

import java.util.ArrayList;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.ListChoice;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.Modal;
import de.agilecoders.wicket.core.markup.html.bootstrap.dialog.ModalCloseButton;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.ProjectMemberService;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;

public abstract class ProjectMemberModal extends Modal {

    @Inject
    private UserService userService;

    @Inject
    private ProjectService projectService;

    @Inject
    private ProjectMemberService projectMemberService;

    private final ProjectMemberModel projectMemberModel;

    private LoadableDetachableModel<Project> projectModel;

    public ProjectMemberModal(String id, final String projectId) {
        this(id, projectId, null);
    }

    public ProjectMemberModal(final String wicketId, final String projectId, final String projectMemberId) {
        super(wicketId);
        projectMemberModel = new ProjectMemberModel(projectMemberId);
        projectModel = new LoadableDetachableModel<Project>() {
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
                onSave(projectMemberModel);
            }
        };
        add(form);

        final PropertyModel<User> userModel = new PropertyModel<User>(projectMemberModel, "user");
        final ListChoice<User> userSelection = new ListChoice<User>("user", userModel, userService.getAllUsers(), new ChoiceRenderer<User>("username", "id"));
        form.add(userSelection);
        if(projectMemberId != null) {
//            userSelection.add(new AttributeModifier("disabled", "disabled"));
        }
        userSelection.add(new AttributeModifier("size", 1));

        final ListChoice<ProjectRole> projectRoleSelection = new ListChoice<ProjectRole>("projectRole", new PropertyModel<ProjectRole>(projectMemberModel, "projectRole"), new ArrayList<ProjectRole>(projectModel.getObject().getProjectRoles()), new ChoiceRenderer<ProjectRole>("name", "id"));
        form.add(projectRoleSelection);
        projectRoleSelection.add(new AttributeModifier("size", 1));

        addButton(new ModalCloseButton(new ResourceModel("label.cancel")));

        final SubmitLink submitButton = new SubmitLink("button", form);
        addButton(submitButton);
        if(projectMemberId != null) {
            submitButton.setBody(Model.of(getString("label.edit")));
        } else {
            submitButton.setBody(Model.of(getString("label.add")));
        }
        submitButton.add(new ButtonBehavior(Buttons.Type.Success));
    }

    public Project getProject() {
        return projectModel.getObject();
    }

    public abstract void onSave(IModel<ProjectMember> projectMemberModel);

    class ProjectMemberModel extends Model<ProjectMember> {
        private final String id;
        private ProjectMember modelObject;

        public ProjectMemberModel(String projectMemberId) {
            this.id = projectMemberId;
        }

        @Override
        public ProjectMember getObject() {
            if(this.modelObject != null) {
                return this.modelObject;
            }

            if(this.id != null) {
                this.modelObject = projectMemberService.findOne(this.id);
            } else {
                this.modelObject = new ProjectMember();
            }

            return this.modelObject;
        }

        @Override
        public void detach() {
            this.modelObject = null;
        }
    }
}
