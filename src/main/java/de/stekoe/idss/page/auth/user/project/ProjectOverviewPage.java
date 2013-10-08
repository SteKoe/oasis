package de.stekoe.idss.page.auth.user.project;

import de.stekoe.idss.page.auth.user.AuthUserPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectOverviewPage extends AuthUserPage {
    public ProjectOverviewPage() {
        super();

        final BookmarkablePageLink<CreateProjectPage> createProject = new BookmarkablePageLink<CreateProjectPage>("createProject", CreateProjectPage.class);
        add(createProject);
    }
}
