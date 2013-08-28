package de.stekoe.idss.dao;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.TestFactory;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;

public class ProjectDAOTestCase extends BaseTest {

    @Autowired
    private ProjectDAO projectDAO;

    @Test
    public void differentProjectMembers() throws Exception {
        User projectLeader = TestFactory.createUser("Project Leader");
        User projectMember = TestFactory.createUser("Project Member");

        Project p = new Project();
        p.setProjectName("Test Project");
        p.getProjectLeader().add(projectLeader);
        p.getProjectLeader().add(projectMember);

        projectDAO.save(p);

        List<Project> projects = projectDAO.findByProjectName("Test Project");
        Project project = projects.get(0);
        assertTrue(project.getProjectLeader().contains(projectLeader));
        assertTrue(project.getProjectMember().contains(projectMember));
    }

}
