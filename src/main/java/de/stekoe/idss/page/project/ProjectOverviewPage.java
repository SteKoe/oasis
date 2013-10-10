package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.IProjectService;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.Model;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectOverviewPage extends AuthUserPage {

    @Inject
    private IProjectService projectService;

    public ProjectOverviewPage() {
        super();

        final BookmarkablePageLink<CreateProjectPage> createProject = new BookmarkablePageLink<CreateProjectPage>("createProject", CreateProjectPage.class);
        add(createProject);

        final List<Project> allProjectsOfUser = projectService.findAllForUser(getSession().getUser());
        add(new ListView<Project>("projectItem", allProjectsOfUser) {
            @Override
            protected void populateItem(ListItem<Project> item) {
                Project p = item.getModelObject();
                final Label projectTitle = new Label("projectTitle", Model.of(p.getName()));
                item.add(projectTitle);
            }
        });
    }
}
