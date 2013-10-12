package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.auth.annotation.ProjectMemberOnly;
import de.stekoe.idss.page.user.UserProfilePage;
import de.stekoe.idss.service.IProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

@ProjectMemberOnly
public class ProjectDetailsPage extends AuthProjectPage {

    @Inject
    IProjectService projectService;

    public ProjectDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        final StringValue id = pageParameters.get("id");
        if(id == null) {
            getSession().error("Project could not be found!");
            redirectToOverviewPage();
            return;
        }

        final Project project = projectService.findById(id.toString());

        if (!isAuthorized(getSession().getUser(), project)) {
            getSession().error("You are not allowed to access this project.");
            redirectToOverviewPage();
            return;
        }

        final Label projectTitle = new Label("projectTitle", Model.of(project.getName()));
        add(projectTitle);

        final Set<ProjectMember> projectTeam = project.getProjectTeam();
        listOfProjectLeader(projectTeam);
        listOfProjectMember(projectTeam);
    }

    private void listOfProjectLeader(Set<ProjectMember> projectTeam) {
        final List<ProjectMember> projectLeaders = (List<ProjectMember>) CollectionUtils.select(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                ProjectMember pm = (ProjectMember) object;
                return pm.isLeader();
            }
        });

        add(new Label("projectLeaderCount", MessageFormat.format(getString("numberOfPersons"), projectLeaders.size())));

        add(new ListView<ProjectMember>("projectLeaderItem", projectLeaders) {
            @Override
            protected void populateItem(ListItem<ProjectMember> item) {
                ProjectMember pm = item.getModelObject();

                PageParameters pageParams = new PageParameters();
                pageParams.add("id", pm.getUser().getId());

                final BookmarkablePageLink<UserProfilePage> userDetailsLink = new BookmarkablePageLink<UserProfilePage>("projectLeaderItemLink", UserProfilePage.class, pageParams);

                Label userName = new Label("projectLeaderItemLabel", Model.of(pm.getUser().getUsername()));
                userDetailsLink.add(userName);

                item.add(userDetailsLink);
            }
        });
    }

    /* Creates necessary ui elements for Project Member section
     */
    private void listOfProjectMember(Set<ProjectMember> projectTeam) {
        final List<ProjectMember> projectMember = (List<ProjectMember>) CollectionUtils.select(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                ProjectMember pm = (ProjectMember) object;
                return !pm.isLeader();
            }
        });

        add(new Label("projectMemberCount", MessageFormat.format(getString("numberOfPersons"), projectMember.size())));

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
        setResponsePage(ProjectOverviewPage.class);
    }

}
