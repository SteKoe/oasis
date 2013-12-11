package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.service.ISystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class SystemRoleService implements ISystemRoleService {

    @Autowired
    ISystemRoleDAO systemRoleDAO;

    @Override
    public List<SystemRole> findAllRoles() {
        return systemRoleDAO.findAll();
    }

    @Override
    public SystemRole getAdminRole() {
        return systemRoleDAO.getRoleByName(SystemRole.ADMIN);
    }

    @Override
    public SystemRole getUserRole() {
        return systemRoleDAO.getRoleByName(SystemRole.USER);
    }

    @Override
    public Map<Integer, String> getAsSelectionList() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}