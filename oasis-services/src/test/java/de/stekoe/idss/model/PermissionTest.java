package de.stekoe.idss.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PermissionTest {

    @Test
    public void hasIdTest() throws Exception {
        final Permission permission = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "abc");
        permission.setObjectId("abc");
        assertTrue(permission.hasObjectId());
    }

    @Test
    public void hasNoIdTest() throws Exception {
        final Permission permission = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, null);
        assertFalse(permission.hasObjectId());
    }
}
