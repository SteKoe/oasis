package de.stekoe.oasis.service;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.stekoe.oasis.AbstractBaseTest;
import de.stekoe.oasis.model.SystemRole;


public class SystemRoleServiceTest extends AbstractBaseTest {
    @Inject
    SystemRoleService systemRoleService;

    @Before
    public void setUp() {
        SystemRole adminRole = new SystemRole();
        adminRole.setName(SystemRole.ADMIN);
        systemRoleService.save(adminRole);

        SystemRole userRole = new SystemRole();
        userRole.setName(SystemRole.USER);
        systemRoleService.save(userRole);
    }

    @Test
    public void baseSystemRolesCanBeRetrieved() throws Exception {
        SystemRole adminRole = systemRoleService.getAdminRole();
        Assert.assertNotNull(adminRole);

        SystemRole userRole = systemRoleService.getAdminRole();
        Assert.assertNotNull(userRole);
    }

}
