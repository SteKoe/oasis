package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.SystemRoleDAO;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.service.ISystemRoleService;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SystemRoleService implements ISystemRoleService {

    @Inject
    SystemRoleDAO systemRoleDAO;

    @Override
    public List<SystemRole> findAllRoles() {
        return systemRoleDAO.getAllRoles();
    }

    @Override
    public SystemRole getAdminRole() {
        return systemRoleDAO.getRoleByName(SystemRole.ADMIN);
    }

    @Override
    public SystemRole getUserRole() {
        return systemRoleDAO.getRoleByName(SystemRole.USER);
    }


}
