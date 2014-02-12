package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.impl.PermissionDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.service.PermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
public class DefaultPermissionService implements PermissionService {
    @Inject
    PermissionDAO permissionDAO;

    @Override
    public void save(Permission permission) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void delete(Permission permission) {
        permissionDAO.delete(permission);
    }
}
