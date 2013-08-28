package de.stekoe.idss.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import de.stekoe.idss.page.AdminAuthUser;

public class UserTest {

    @Test
    public void isAdmin() throws Exception {
        AdminAuthUser admin = new AdminAuthUser();
        assertTrue(admin.isAdmin());
    }

}
