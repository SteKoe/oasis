package de.stekoe.idss.page.project;

import de.stekoe.idss.component.behavior.CustomTinyMCESettings;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.auth.annotation.ProjectMemberOnly;
import de.stekoe.idss.page.user.UserProfilePage;
import de.stekoe.idss.service.IProjectService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import wicket.contrib.tinymce.TinyMceBehavior;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;

@ProjectMemberOnly
public class ProjectDetailsPage extends ProjectPage {

    @Inject
    IProjectService projectService;

    public ProjectDetailsPage(PageParameters pageParameters) {
        super(pageParameters);

        Project project = getProject();

        final Label projectTitle = new Label("projectTitle", Model.of(project.getName()));
        add(projectTitle);

        // TinyMCE
        final TextArea descriptionTextArea = new TextArea("descriptionTextArea", Model.of(project.getDescription()));
        add(descriptionTextArea);
        final TinyMceBehavior tinyMceBehavior = new TinyMceBehavior(CustomTinyMCESettings.getStandard());
        descriptionTextArea.add(tinyMceBehavior);

        // Description
        final Label descriptionText = new Label("descriptionText", Model.of(project.getDescription()));
        descriptionText.setEscapeModelStrings(false);
        add(descriptionText);

        if(project.userHasRole(ProjectRole.LEADER, getSession().getUser())) {
            descriptionText.setVisible(false);
            descriptionTextArea.setVisible(true);
        } else {
            descriptionText.setVisible(true);
            descriptionTextArea.setVisible(false);
        }

        final BookmarkablePageLink<ProjectAddMember> addMemberLink = new BookmarkablePageLink<ProjectAddMember>("addMember", ProjectAddMember.class, new PageParameters().add("id", project.getId()));
        add(addMemberLink);

        final Collection<ProjectMember> projectTeam = project.getProjectTeam();
        listOfProjectLeader(projectTeam);
        listOfProjectMember(projectTeam);
    }

    private void listOfProjectLeader(Collection<ProjectMember> projectTeam) {
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
    private void listOfProjectMember(Collection<ProjectMember> projectTeam) {
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
