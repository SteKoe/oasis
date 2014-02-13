package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.impl.PermissionDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultPermissionService implements PermissionService {

    @Autowired
    PermissionDAO permissionDAO;

    @Override
    public void save(Permission permission) {
        permissionDAO.save(permission);
    }

    @Override
    public void delete(Permission permission) {
        permissionDAO.delete(permission);
    }
}
