package de.stekoe.idss.page.project;

import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.ProjectMember;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.page.auth.annotation.ProjectLeaderOnly;
import de.stekoe.idss.service.IProjectService;
import de.stekoe.idss.service.IUserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@ProjectLeaderOnly
public class ProjectAddMember extends ProjectPage {

    @Inject
    IUserService userService;

    @Inject
    IProjectService projectService;

    public ProjectAddMember(PageParameters pageParameters) {
        super(pageParameters);

        User newUser = new User();
        newUser.setUsername("Test Project Member");
        newUser.setEmail("test@stekoe.de");
        newUser.setUserStatus(UserStatus.TEST);

        try {
            userService.save(newUser);
        } catch(Exception e) {
            return;
        }

        ProjectMember member = new ProjectMember();
        member.setUser(newUser);
        member.getProjectRoles().add(ProjectRole.MEMBER);

        final Project project = getProject();
        project.getProjectTeam().add(member);
        projectService.update(project);
    }
}
