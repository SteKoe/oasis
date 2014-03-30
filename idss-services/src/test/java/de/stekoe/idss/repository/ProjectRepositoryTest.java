package de.stekoe.idss.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.project.Project;
import de.stekoe.idss.model.project.ProjectMember;
import de.stekoe.idss.service.ProjectService;
import de.stekoe.idss.service.UserService;

public class ProjectRepositoryTest extends AbstractBaseTest {

    @Inject
    ProjectService projectService;

    @Inject
    UserService userService;

    @Test
    public void findByUserTest() throws Exception {
        User user = TestFactory.createUser("hans.peter");
        userService.save(user);

        ProjectMember projectMember = TestFactory.createProjectMember();
        projectMember.setUser(user);

        Project project = TestFactory.createProject();
        project.getProjectTeam().add(projectMember);

        projectService.save(project);

        List<Project> usersProjects = projectService.findByUser(user.getId());
        assertFalse(usersProjects.isEmpty());
        assertTrue(usersProjects.size() == 1);
        assertTrue(usersProjects.get(0).getId().equals(project.getId()));
    }

}
