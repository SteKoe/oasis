package de.stekoe.idss.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.junit.Test;

import de.stekoe.idss.AbstractBaseTest;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.PermissionObject;
import de.stekoe.idss.model.PermissionType;
import de.stekoe.idss.model.project.ProjectId;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.repository.ProjectRoleRepository;
import de.stekoe.idss.service.ProjectRoleService;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class ProjectRoleServiceTest extends AbstractBaseTest {
    @Inject
    ProjectRoleService projectRoleService;

    @Test
    public void editPermissionsOfProjectRole() throws Exception {
        ProjectRole projectRole = new ProjectRole();
        projectRole.setName("ProjectRole");
        final ProjectId projectId = new ProjectId();
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findOne(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        Permission permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertEquals(projectId.getId(), permission.getObjectId().getId());

        projectRole.getPermissions().clear();
        final ProjectId projectId2 = new ProjectId();
        projectRole.getPermissions().add(new Permission(PermissionObject.PROJECT, PermissionType.UPDATE, projectId2));
        projectRoleService.save(projectRole);

        projectRole = projectRoleService.findOne(projectRole.getId());
        assertTrue(projectRole.getPermissions().size() == 1);
        permission = projectRole.getPermissions().toArray(new Permission[1])[0];
        assertEquals(projectId2.getId(), permission.getObjectId().getId());
    }
}
