package de.stekoe.idss.dao.impl;

import de.stekoe.idss.dao.IPermissionDAO;
import de.stekoe.idss.model.Permission;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Repository
public class PermissionDAO extends GenericDAO<Permission> implements IPermissionDAO {
    @Override
    protected Class getPersistedClass() {
        return Permission.class;
    }
}
