package de.stekoe.idss.model;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SystemRoleTest {
    private SystemRole systemRole;

    @Before
    public void setUp() throws Exception {
        systemRole = new SystemRole();
        systemRole.setName("ABC");
    }

    @Test
    public void testEquals() throws Exception {
        SystemRole role1 = new SystemRole("ADMIN");
        SystemRole role2 = new SystemRole("ADMIN");
        assertThat(role1.equals(role1), equalTo(true));
        assertThat(role1.equals(role2), equalTo(true));
    }

    @Test
    public void testHashCode() throws Exception {

    }
}
