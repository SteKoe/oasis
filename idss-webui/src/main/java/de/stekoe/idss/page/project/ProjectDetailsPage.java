package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.service.ProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

public class ProjectDetailsPage extends ProjectPage {

    @SpringBean ProjectService projectService;

    public ProjectDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        Project project = getProject();

        // Project Title
        add(new Label("projectTitle", Model.of(project.getName())));
        add(new Label("project.status", Model.of(getString(project.getProjectStatus().getKey()))));

        // Description
        String description = project.getDescription();
        if(StringUtils.isEmpty(description)) {
            description = getString("label.description.na");
        }
        final Label descriptionText = new Label("descriptionText", Model.of(description));
        add(descriptionText);
        descriptionText.setEscapeModelStrings(false);

        final BookmarkablePageLink<ProjectEditPage> editProjectLink = new BookmarkablePageLink<ProjectEditPage>("editProject", ProjectEditPage.class, new PageParameters().add("id", project.getId()));
        add(editProjectLink);
        editProjectLink.add(new Label("editProjectLinkLabel", Model.of(getString("label.project.edit"))));
        editProjectLink.setVisibilityAllowed(projectService.isAuthorized(getUser().getId(), project.getId(), PermissionType.UPDATE));

        final BookmarkablePageLink<ProjectEditMemberPage> addMemberLink = new BookmarkablePageLink<ProjectEditMemberPage>("addMember", ProjectEditMemberPage.class, new PageParameters().add("id", project.getId()));
        add(addMemberLink);
        addMemberLink.add(new Label("addProjectMemberLink", Model.of(getString("label.project.edit.member"))));
        addMemberLink.setVisibilityAllowed(projectService.isAuthorized(getUser().getId(), project.getId(), PermissionType.PROJECT_EDIT_MEMBER));

        final Collection<ProjectMember> projectTeam = project.getProjectTeam();
        listOfProjectMember(projectTeam);
    }

    /* Creates necessary ui elements for Project Member section
     */
    private void listOfProjectMember(Collection<ProjectMember> projectTeam) {
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

    private void redirectToOverviewPage() {
        setResponsePage(ProjectListPage.class);
    }

}
