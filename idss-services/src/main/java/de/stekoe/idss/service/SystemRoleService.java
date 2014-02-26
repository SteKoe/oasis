package de.stekoe.idss.service;

import de.stekoe.idss.model.SystemRole;

import java.util.List;
import java.util.Map;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface SystemRoleService {

    /**
     * Get all system roles available in the system.
     * @return a list of system roles
     */
    List<SystemRole> findAllRoles();

    /**
     * Returns the Admin Role
     * @return the Admin SystemRole
     */
    SystemRole getAdminRole();

    /**
     * Returns the User Role
     * @return the User SystemRole
     */
    SystemRole getUserRole();

    void delete(SystemRole entity);

    void save(SystemRole systemRole);

    SystemRole findById(String id);
}
