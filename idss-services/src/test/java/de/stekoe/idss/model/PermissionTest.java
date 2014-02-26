package de.stekoe.idss.model;

import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class PermissionTest {

    @Test
    public void hasIdTest() throws Exception {
        final Permission permission = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "0000");
        permission.setObjectId("whatever");
        assertTrue(permission.hasObjectId());
    }

    @Test
    public void hasNoIdTest() throws Exception {
        final Permission permission = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, null);
        assertFalse(permission.hasObjectId());
    }
}
