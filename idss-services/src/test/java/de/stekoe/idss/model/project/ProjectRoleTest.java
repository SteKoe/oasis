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

import static org.junit.Assert.assertEquals;
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
        final ProjectId projectId = new ProjectId();
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findById(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        Permission permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertEquals(projectId.getId(), permission.getObjectId().getId());

        projectRole.getPermissions().clear();
        final ProjectId projectId2 = new ProjectId();
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId2));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findById(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertEquals(projectId2.getId(), permission.getObjectId().getId());
    }
}
