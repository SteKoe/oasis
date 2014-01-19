package de.stekoe.idss.component.form.project;

import de.stekoe.idss.component.modal.AddProjectMemberModal;
import de.stekoe.idss.component.user.UserInfoBlock;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.commons.lang3.StringUtils;
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

    @SpringBean private ProjectService projectService;
    private final LoadableDetachableModel<Project> model;

    private final String projectId;

    public EditProjectMembersForm(String id, final String projectId) {
        super(id);
        this.projectId = projectId;
        this.model = new LoadableDetachableModel<Project>(){
            @Override
            protected Project load() {
                return projectService.findById(projectId);
            }
        };

        final Button buttonAddMember = new Button("button.add.member");
        add(buttonAddMember);

        final AddProjectMemberModal addProjectMemberModal = new AddProjectMemberModal("modal.add.member") {
            @Override
            public void onSave(ProjectMember projectMember) {
                LOG.error(projectMember);
                if(projectMember.getUser() != null) {
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

                final UserInfoBlock userInfoBlock = new UserInfoBlock("user.info.block", user, StringUtils.join(pm.getProjectRoles().iterator(), ", "));
                item.add(userInfoBlock);

                final BookmarkablePageLink<HomePage> editUserLink = new BookmarkablePageLink<HomePage>("user.edit", HomePage.class);
                item.add(editUserLink);
                editUserLink.setVisible(projectService.isAuthorized(WebSession.get().getUser().getId(), project.getId(), PermissionType.PROJECT_EDIT_MEMBER));

                final BookmarkablePageLink<HomePage> deleteUserLink = new BookmarkablePageLink<HomePage>("user.delete", HomePage.class);
                item.add(deleteUserLink);
                deleteUserLink.setVisible(projectService.isAuthorized(WebSession.get().getUser().getId(), project.getId(), PermissionType.PROJECT_EDIT_MEMBER) && !user.getId().equals(WebSession.get().getUser().getId()));
            }
        };
        add(projectMembersList);
    }
}
