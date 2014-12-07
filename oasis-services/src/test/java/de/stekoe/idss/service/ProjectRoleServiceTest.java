package de.stekoe.idss.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.IDGenerator;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.ProjectRole;

public class ProjectRoleServiceTest extends AbstractBaseTest {
    @Inject
    ProjectRoleService projectRoleService;

    @Test
    public void editPermissionsOfProjectRole() throws Exception {
        ProjectRole projectRole = new ProjectRole();
        projectRole.setName("ProjectRole");

        final String projectId = "";
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findOne(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        Permission permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertEquals(projectId, permission.getObjectId());

        projectRole.getPermissions().clear();
        final String projectId2 = IDGenerator.createId();
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId2));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findOne(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertEquals(projectId2, permission.getObjectId());
    }
}
