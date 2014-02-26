package de.stekoe.idss.model;

import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class PermissionTest {

    private Permission permission;

    @Before
    public void setUp() {
        this.permission = new Permission();
    }

    @Test
    public void constructorTest() throws Exception {
        final Permission permission = new Permission(PermissionObject.PROJECT, PermissionType.READ, "ObjectId");
        assertThat(permission.getPermissionObject(), IsEqual.equalTo(PermissionObject.PROJECT));
        assertThat(permission.getPermissionType(), IsEqual.equalTo(PermissionType.READ));
        assertThat(permission.getObjectId(), IsEqual.equalTo("ObjectId"));
    }

    @Test
    public void hasIdTest() throws Exception {
        final Permission permission = new Permission(PermissionObject.PROJECT, PermissionType.CREATE, "0000");
        permission.setObjectId("whatever");
        assertTrue(permission.hasObjectId());
    }

    @Test
    public void getId() throws Exception {
        final String expected = "ABC";
        permission.setId(expected);
        assertThat(permission.getId(), IsEqual.equalTo(expected));
    }

    @Test
    public void getObjectId() throws Exception {
        final String expected = "ObjectID";
        permission.setObjectId(expected);
        assertThat(permission.getObjectId(), IsEqual.equalTo(expected));
    }

    @Test
    public void getObjectType() throws Exception {
        final PermissionObject expected = PermissionObject.PROJECT;
        permission.setPermissionObject(expected);
        assertThat(permission.getPermissionObject(), IsEqual.equalTo(expected));
    }

    @Test
    public void getPermissionType() throws Exception {
        final PermissionType expected = PermissionType.CREATE;
        permission.setPermissionType(expected);
        assertThat(permission.getPermissionType(), IsEqual.equalTo(expected));
    }

}
