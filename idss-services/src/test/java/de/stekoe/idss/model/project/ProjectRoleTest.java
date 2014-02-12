package de.stekoe.idss.model.project;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.enums.PermissionObject;
import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.service.ProjectRoleService;
import org.hamcrest.core.IsEqual;
import org.junit.Test;

import javax.inject.Inject;

import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectRoleTest extends AbstractBaseTest {
    @Inject
    IProjectRoleDAO projectRoleDAO;

    @Inject
    ProjectRoleService projectRoleService;

    @Test
    public void editPermissionsOfProjectRole() throws Exception {
        ProjectRole projectRole = new ProjectRole();
        projectRole.setName("ProjectRole");
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, "123"));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findById(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        Permission permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertThat(permission.getObjectId(), IsEqual.equalTo("123"));

        projectRole.getPermissions().clear();
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, "456"));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findById(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertThat(permission.getObjectId(), IsEqual.equalTo("456"));
    }
}
