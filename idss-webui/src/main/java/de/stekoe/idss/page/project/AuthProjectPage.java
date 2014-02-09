package de.stekoe.idss.page.project;

import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;
import org.apache.log4j.Logger;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class AuthProjectPage extends AuthUserPage {
    private static final Logger LOG = Logger.getLogger(AuthProjectPage.class);

    @SpringBean
    private ProjectService projectService;

    private final String projectId;
    private Class<? extends IRequestablePage> responsePage = ProjectListPage.class;
    private LoadableDetachableModel<Project> projectModel;

    public AuthProjectPage(PageParameters pageParameters) {

        final StringValue idParam = pageParameters.get("id");
        if (idParam == null || idParam.isEmpty()) {
            projectId = null;
            getSession().error("Project could not be found!");
            setResponsePage(getResponsePage());
            return;
        } else {
            projectId = idParam.toString();
        }

        if (!projectService.isAuthorized(getUser().getId(), projectId, PermissionType.READ)) {
            WebSession.get().error("You are not allowed to access this project!");
            setResponsePage(getResponsePage());
        }

        projectModel = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                LOG.info("Project with id '" + projectId + "' loaded from database!");
                return projectService.findById(projectId);
            }
        };
    }

    private Class<? extends IRequestablePage> getResponsePage() {
        return responsePage;
    }

    /**
     * Allows to set different redirection page which defaults to ProjectOverviewPage
     *
     * @param responsePage
     */
    public void setRedirectPage(Class<? extends IRequestablePage> responsePage) {
        this.responsePage = responsePage;
    }

    public String getProjectId() {
        return projectId;
    }

    /**
     * Get the project identified by the id value in ProjectPage
     *
     * @return The current project
     */
    public Project getProject() {
        return projectModel.getObject();
    }
}
