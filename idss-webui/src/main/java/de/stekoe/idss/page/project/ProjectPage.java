package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.auth.annotation.ProjectLeaderOnly;
import de.stekoe.idss.page.auth.annotation.ProjectMemberOnly;
import de.stekoe.idss.service.IProjectService;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.string.StringValue;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public abstract class ProjectPage extends AuthUserPage {

    @Inject
    private IProjectService projectService;
    private Class<? extends IRequestablePage> responsePage = ProjectOverviewPage.class;

    private Project project = null;

    public ProjectPage(PageParameters pageParameters) {

        final StringValue id = pageParameters.get("id");
        if(id == null || id.isEmpty()) {
            getSession().error("Project could not be found!");
            setResponsePage(getResponsePage());
            return;
        }


        project = projectService.findById(id.toString());
        if (project == null) {
            getSession().error("Project could not be found!");
            setResponsePage(getResponsePage());
            return;
        }

        final ProjectLeaderOnly projectLeaderOnly = this.getClass().getAnnotation(ProjectLeaderOnly.class);
        final ProjectMemberOnly projectMemberOnly = this.getClass().getAnnotation(ProjectMemberOnly.class);

        if(projectMemberOnly != null) {
            if(!project.userIsMember(getSession().getUser())) {
                getSession().error("You must be a member of this Project in order to access this page!");
                setResponsePage(getResponsePage());
                return;
            }
        }

        if(projectLeaderOnly != null) {
            if(!project.userHasRole(ProjectRole.LEADER, getSession().getUser())) {
                getSession().error("You must be a leader of this Project in order to access this page!");
                setResponsePage(getResponsePage());
                return;
            }
        }
    }

    private Class<? extends IRequestablePage> getResponsePage() {
        return responsePage;
    }

    /**
     * Allows to set different redirection page which defaults to ProjectOverviewPage
     * @param responsePage
     */
    public void setRedirectPage(Class<? extends IRequestablePage> responsePage) {
        this.responsePage = responsePage;
    }

    /**
     * Get the project identified by the id value in ProjectPage
     * @return The current project
     */
    public Project getProject() {
        return this.project;
    }
}
