package de.stekoe.idss.page.project;

import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.enums.ProjectStatus;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.service.ProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class ProjectPage extends AuthProjectPage {

    @SpringBean ProjectService projectService;

    public ProjectPage(PageParameters pageParameters) {
        super(pageParameters);

        addProjectTitle();
        addProjectStatus();

        addLinkProjectOverview();
        addLinkProjectDelete();

        addUploadDocumentLink();
        addEditProjectLink();
        addEditProjectMemberLink();
        addEditProjectRolesLink();

        addListOfProjectMember();
    }

    private void addUploadDocumentLink() {
        final BookmarkablePageLink<ProjectUploadDocument> linkUploadPage = new BookmarkablePageLink<ProjectUploadDocument>("link.upload.document", ProjectUploadDocument.class, getPageParameters());
        add(linkUploadPage);
    }


    private void addLinkProjectDelete() {
        final Link<Void> linkProjectDelete = new Link<Void>("link.project.delete") {
            @Override
            public void onClick() {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        };
        add(linkProjectDelete);
        linkProjectDelete.setVisible(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.DELETE));
    }

    private void addLinkProjectOverview() {
        final BookmarkablePageLink<ProjectDetailsPage> linkProjectOverview = new BookmarkablePageLink<ProjectDetailsPage>("link.project.overview", ProjectDetailsPage.class, getPageParameters());
        add(linkProjectOverview);
    }

    private void addProjectStatus() {
        final Label projectStatusLabel = new Label("project.status", Model.of(getString(getProject().getProjectStatus().getKey())));
        add(projectStatusLabel);
        projectStatusLabel.setVisible(!getProject().getProjectStatus().equals(ProjectStatus.UNDEFINED));
    }

    private MarkupContainer addProjectTitle() {
        return add(new Label("projectTitle", Model.of(getProject().getName())));
    }

    private void addEditProjectRolesLink() {
        final BookmarkablePageLink<ProjectRoleListPage> editProjectRolesLink = new BookmarkablePageLink<ProjectRoleListPage>("editProjectRolesLink", ProjectRoleListPage.class, new PageParameters().add("id", getProject().getId()));
        add(editProjectRolesLink);
        editProjectRolesLink.setVisibilityAllowed(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE_PROJECT_ROLES));
    }

    private void addEditProjectMemberLink() {
        final BookmarkablePageLink<ProjectMemberListPage> addMemberLink = new BookmarkablePageLink<ProjectMemberListPage>("addMember", ProjectMemberListPage.class, new PageParameters().add("id", getProject().getId()));
        add(addMemberLink);
        addMemberLink.setVisibilityAllowed(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE_PROJECT_MEMBER));
    }

    private void addEditProjectLink() {
        final BookmarkablePageLink<ProjectEditPage> editProjectLink = new BookmarkablePageLink<ProjectEditPage>("editProject", ProjectEditPage.class, new PageParameters().add("id", getProject().getId()));
        add(editProjectLink);
        editProjectLink.setVisibilityAllowed(projectService.isAuthorized(getUser().getId(), getProject().getId(), PermissionType.UPDATE));
    }

    /* Creates necessary ui elements for Project Member section
     */
    private void addListOfProjectMember() {
        final Collection<ProjectMember> projectTeam = getProject().getProjectTeam();
        final List<ProjectMember> projectMember = (List<ProjectMember>) CollectionUtils.select(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                ProjectMember pm = (ProjectMember) object;
                return true;
            }
        });

        add(new Label("projectMemberCount", MessageFormat.format(getString("label.project.numOfPersons"), projectMember.size())));

        add(new ListView<ProjectMember>("projectMemberItem", projectMember) {
            @Override
            protected void populateItem(ListItem<ProjectMember> item) {
                ProjectMember pm = item.getModelObject();

                final BookmarkablePageLink<HomePage> userDetailsLink = new BookmarkablePageLink<HomePage>("projectMemberItemLink", HomePage.class);

                Label userName = new Label("projectMemberItemLabel", Model.of(pm.getUser().getUsername()));
                userDetailsLink.add(userName);

                item.add(userDetailsLink);
            }
        });
    }
}
