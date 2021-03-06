package de.stekoe.idss.page.project;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.component.IRequestablePage;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.session.WebSession;

public abstract class AuthProjectPage extends AuthUserPage {
    private static final Logger LOG = Logger.getLogger(AuthProjectPage.class);

    @SpringBean
    private ProjectService projectService;

    private final Class<? extends IRequestablePage> responsePage = ProjectListPage.class;
    private final ProjectModel projectModel;

    public AuthProjectPage(PageParameters pageParameters) {
        super(pageParameters);

        final StringValue projectIdParam = pageParameters.get("projectId");
        projectModel = new ProjectModel(projectIdParam.toString());

        if (projectModel.getObject() == null) {
            WebSession.get().error(getString("message.accessdenied"));
            throw new RestartResponseException(ProjectListPage.class);
        }

        if (!projectService.isAuthorized(getUser().getId(), getProjectId(), PermissionType.READ)) {
            WebSession.get().error(getString("message.accessdenied"));
            throw new RestartResponseException(ProjectListPage.class);
        }
    }

    /**
     * @return The project id
     */
    public String getProjectId() {
        if(getProject() == null) {
            return null;
        } else {
            return getProject().getId();
        }
    }

    public ProjectModel getProjectModel() {
        return projectModel;
    }

    /**
     * Get the project identified by the id value in ProjectPage
     *
     * @return The current project
     */
    public Project getProject() {
        return (Project) getProjectModel().getObject();
    }

    public class ProjectModel extends Model {

        private String projectId;
        private Project project;

        public ProjectModel() {

        }

        public ProjectModel(String projectId) {
            this.projectId = projectId;
        }

        public ProjectModel(Project project) {
            setObject(project);
        }

        @Override
        public Serializable getObject() {
            if(project != null) {
                return project;
            }

            if(projectId == null) {
                return new Project();
            } else {
                project = projectService.findOne(projectId);
                return project;
            }
        }

        @Override
        public void setObject(Serializable object) {
            project = (Project) object;
            projectId = (project != null) ? project.getId() : null;
        }

        @Override
        public void detach() {
            project = null;
        }
    }
}
