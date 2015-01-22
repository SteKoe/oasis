package de.stekoe.oasis.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void testEqualsOnSameObject() throws Exception {
        assertTrue(user.equals(user));
    }

    @Test
    public void testEqualsWithNull() throws Exception {
        assertFalse(user.equals(null));
    }

    @Test
    public void testEqualsWithDifferentObject() throws Exception {
        final UserProfile userProfile = new UserProfile();
        assertFalse(user.equals(userProfile));
    }

    @Test
    public void testHasAnyRoleWithNull() throws Exception {
        user.hasAnyRole(null);
    }

    @Test
    public void testHasAnyRoleWithEmtpyList() throws Exception {
        user.hasAnyRole(new ArrayList<SystemRole>());
    }
}
