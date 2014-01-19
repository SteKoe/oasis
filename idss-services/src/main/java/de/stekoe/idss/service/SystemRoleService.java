package de.stekoe.idss.service;

import de.stekoe.idss.model.SystemRole;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
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

    /**
     * @return
     */
    Map<Integer, String> getAsSelectionList();

    void save(SystemRole systemRole);
}