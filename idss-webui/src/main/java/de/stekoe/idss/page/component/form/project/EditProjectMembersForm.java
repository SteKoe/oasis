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

package de.stekoe.idss.page.component.form.project;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.component.modal.AddProjectMemberModal;
import de.stekoe.idss.page.component.user.UserInfoBlock;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.util.ArrayList;
import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class EditProjectMembersForm extends Panel {

    private static final Logger LOG = Logger.getLogger(EditProjectMembersForm.class);

    @SpringBean
    private ProjectService projectService;
    private final LoadableDetachableModel<Project> model;

    private final String projectId;

    public EditProjectMembersForm(String id, final String projectId) {
        super(id);
        this.projectId = projectId;
        this.model = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                return projectService.findById(projectId);
            }
        };

        final Button buttonAddMember = new Button("button.add.member");
        add(buttonAddMember);

        final AddProjectMemberModal addProjectMemberModal = new AddProjectMemberModal("modal.add.member", projectId) {
            @Override
            public void onSave(ProjectMember projectMember) {
                LOG.error(projectMember);
                if (projectMember.getUser() != null) {
                    Project project = model.getObject();
                    project.getProjectTeam().add(projectMember);
                    projectService.save(project);
                }

                setResponsePage(getPage().getClass(), getPage().getPageParameters());
            }
        };
        add(addProjectMemberModal);
        addProjectMemberModal.addOpenerAttributesTo(buttonAddMember);

        final Project project = model.getObject();
        final Set<ProjectMember> projectTeam = project.getProjectTeam();
        final ListView<ProjectMember> projectMembersList = new ListView<ProjectMember>("members", new ArrayList<ProjectMember>(projectTeam)) {
            @Override
            protected void populateItem(ListItem item) {
                ProjectMember pm = (ProjectMember) item.getModelObject();
                final User user = pm.getUser();

                final UserInfoBlock userInfoBlock = new UserInfoBlock("user.info.block", user, pm.getProjectRole().toString());
                item.add(userInfoBlock);

                final BookmarkablePageLink<HomePage> editUserLink = new BookmarkablePageLink<HomePage>("user.edit", HomePage.class);
                item.add(editUserLink);
                editUserLink.setVisible(projectService.isAuthorized(getCurrentUser().getId(), project.getId(), PermissionType.PROJECT_ADD_MEMBER));

                final BookmarkablePageLink<HomePage> deleteUserLink = new BookmarkablePageLink<HomePage>("user.delete", HomePage.class);
                item.add(deleteUserLink);
                deleteUserLink.setVisible(projectService.isAuthorized(getCurrentUser().getId(), project.getId(), PermissionType.PROJECT_ADD_MEMBER) && !user.getId().equals(getCurrentUser().getId()));
            }
        };
        add(projectMembersList);
    }

    private User getCurrentUser() {
        return WebSession.get().getUser();
    }
}
