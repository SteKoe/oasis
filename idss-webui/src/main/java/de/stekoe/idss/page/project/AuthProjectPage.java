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

    private Class<? extends IRequestablePage> responsePage = ProjectListPage.class;
    private LoadableDetachableModel<Project> projectModel;

    public AuthProjectPage(PageParameters pageParameters) {
        super(pageParameters);

        final StringValue projectId = pageParameters.get("projectId");
        projectModel = new LoadableDetachableModel<Project>() {
            @Override
            protected Project load() {
                return projectService.findById(projectId.toString());
            }
        };
        if(projectModel.getObject() == null) {
            setResponsePage(ProjectListPage.class);
        }

        if (!projectService.isAuthorized(getUser().getId(), projectId.toString(), PermissionType.READ)) {
            WebSession.get().error("You are not allowed to access this project!");
            setResponsePage(ProjectListPage.class);
        }
    }

    /**
     * @return The project id
     */
    public String getProjectId() {
        return getProject().getId();
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
