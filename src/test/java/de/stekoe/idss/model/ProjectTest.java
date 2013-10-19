package de.stekoe.idss.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectTest {

    private ProjectRole leaderRole = ProjectRole.LEADER;
    private ProjectRole memberRole = ProjectRole.MEMBER;

    private User leaderUser;
    private User memberUser;

    @Before
    public void setUp() {
        leaderUser = new User();
        leaderUser.setId("123");
        leaderUser.setUsername("Projektleitung");

        memberUser = new User();
        memberUser.setId("456");
        memberUser.setUsername("Projektmember");
    }

    @Test
    public void test() throws Exception {
        final ProjectMember projectMember = new ProjectMember();
        projectMember.setUser(leaderUser);
        projectMember.getProjectRoles().add(leaderRole);

        Set<ProjectMember> projectTeam = new HashSet<ProjectMember>(0);
        projectTeam.add(projectMember);

        Project p = new Project();
        p.setProjectTeam(projectTeam);
        final Collection<ProjectRole> projectRolesForUser = p.getProjectRolesForUser(leaderUser);

        assertNotNull(projectRolesForUser);
        assertTrue(projectRolesForUser.contains(leaderRole));
        assertFalse(projectRolesForUser.contains(memberRole));
    }
}
