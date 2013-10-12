package de.stekoe.idss.page.project;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.page.AuthUserPage;
import de.stekoe.idss.page.auth.annotation.ProjectLeaderOnly;
import de.stekoe.idss.page.auth.annotation.ProjectMemberOnly;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.Set;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class AuthProjectPage extends AuthUserPage {

    private static final Logger LOG = Logger.getLogger(AuthProjectPage.class);

    /**
     * Construct.
     */
    public AuthProjectPage() {
        super();
    }

    /**
     * Construct.
     *
     * @param model IModel of the page
     */
    public AuthProjectPage(IModel<?> model) {
        super(model);
    }

    /**
     * Construct.
     *
     * @param parameters Wrapped page parameters
     */
    public AuthProjectPage(PageParameters parameters) {
        super(parameters);
    }

    /**
     * @param user
     * @param project
     * @return
     */
    protected boolean isAuthorized(User user, Project project) {
        final ProjectMemberOnly projectMemberOnly = this.getClass().getAnnotation(ProjectMemberOnly.class);
        final ProjectLeaderOnly projectLeaderOnly = this.getClass().getAnnotation(ProjectLeaderOnly.class);

        if (projectMemberOnly != null) {
            if (isUserProjectMember(user, project) == false) {
                return false;
            }
        }

        if (projectLeaderOnly != null) {
            if (isUserProjectLeader(user, project) == false) {
                return false;
            }
        }

        return true;
    }

    private boolean isUserProjectLeader(final User user, final Project project) {
        final Set<ProjectMember> projectTeam = project.getProjectTeam();

        ProjectMember pm = (ProjectMember) CollectionUtils.find(projectTeam, new Predicate() {
            @Override
            public boolean evaluate(Object object) {
                ProjectMember pm = (ProjectMember) object;
                return pm.getUser().equals(user);
            }
        });

        if (pm.getProjectRoles().contains(ProjectRole.LEADER)) {
            LOG.info("'" + user.getUsername() + "' is leader of project '" + project.getName() + "'");
            return true;
        } else {
            return false;
        }
    }

    private boolean isUserProjectMember(User user, Project project) {
        for (ProjectMember pm : project.getProjectTeam()) {
            if (pm.getUser().equals(user)) {
                LOG.info("'" + user.getUsername() + "' is member of project '" + project.getName() + "'");
                return true;
            }
        }

        return false;
    }
}
