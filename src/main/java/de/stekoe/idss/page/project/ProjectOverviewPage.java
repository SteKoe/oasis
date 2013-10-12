package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.IProjectRoleService;
import de.stekoe.idss.service.IProjectService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.JavascriptEventConfirmation;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;
import java.text.MessageFormat;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectOverviewPage extends AuthUserPage {

    @Inject
    private IProjectService projectService;

    @Inject
    IProjectRoleService projectRoleService;

    public ProjectOverviewPage() {
        super();

        final BookmarkablePageLink<CreateProjectPage> createProject = new BookmarkablePageLink<CreateProjectPage>("createProject", CreateProjectPage.class);
        add(createProject);

        final List<Project> allProjectsOfUser = projectService.findAllForUser(getSession().getUser());
        add(new ListView<Project>("projectItem", allProjectsOfUser) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                final Project p = item.getModelObject();
                final Label projectTitle = new Label("projectTitle", Model.of(p.getName()));
                item.add(projectTitle);

                // Details link
                PageParameters pageDetailsParameters = new PageParameters();
                pageDetailsParameters.add("id", p.getId());

                BookmarkablePageLink<ProjectDetailsPage> detailsPage = new BookmarkablePageLink<ProjectDetailsPage>("showProjectDetailsLink", ProjectDetailsPage.class, pageDetailsParameters);
                detailsPage.setBody(Model.of("Details"));
                item.add(detailsPage);

                // Delete link
                final Link deleteLink = new Link("deleteProjectLink") {
                    @Override
                    public void onClick() {
                        deleteProject(p.getId());
                    }

                    @Override
                    public boolean isVisible() {
                        if(p.getProjectRolesForUser(WebSession.get().getUser()).contains(projectRoleService.getProjectLeaderRole())) {
                            return true;
                        }

                        return false;
                    }
                };
                deleteLink.add(new JavascriptEventConfirmation("onClick", String.format(getString("project.delete.confirm"), p.getName())));
                deleteLink.setBody(Model.of("Löschen"));
                item.add(deleteLink);
            }
        });
    }

    private void deleteProject(String id) {

    }
}
