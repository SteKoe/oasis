package de.stekoe.idss.model;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void equalsTest() throws Exception {
        Permission p1 = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "abc");
        Permission p2 = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "abc");
        assertThat(p1.equals(p2), is(true));
    }

    @Test
    public void hashCodeTest() throws Exception {
        Permission p1 = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "abc");
        Permission p2 = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "abc");
        assertThat(p1.hashCode() == p2.hashCode(), is(true));
    }

    @Test
    public void contains() throws Exception {
        List<Permission> permissions = new ArrayList<>();
        permissions.add(new Permission(PermissionObject.COMPANY, PermissionType.CREATE, "1"));
        permissions.add(new Permission(PermissionObject.COMPANY, PermissionType.READ, "2"));

        Permission p1 = new Permission(PermissionObject.COMPANY, PermissionType.CREATE, "1");
        assertThat(permissions.contains(p1), is(true));

        Permission p2 = new Permission(PermissionObject.COMPANY, PermissionType.READ, "2");
        assertThat(permissions.contains(p2), is(true));
    }
}
