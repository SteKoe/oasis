package de.stekoe.idss.model;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class PermissionTest {
    @Test
    public void hasIdTest() throws Exception {
        final Permission permission = new Permission();
        permission.setObjectId("whatever");
        assertTrue(permission.hasObjectId());
    }
}
