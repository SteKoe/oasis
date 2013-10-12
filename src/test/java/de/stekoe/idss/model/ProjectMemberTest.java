package de.stekoe.idss.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectMemberTest {
    @Test
    public void userIsMemberOfProject() throws Exception {
        User member = new User();

        ProjectMember pm = new ProjectMember();
        pm.setUser(member);
        pm.getProjectRoles().add(ProjectRole.MEMBER);

        assertTrue(pm.isLeader());
    }
}
