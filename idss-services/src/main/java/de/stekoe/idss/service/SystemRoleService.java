package de.stekoe.idss.service;

import de.stekoe.idss.model.SystemRole;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface SystemRoleService {

    /**
     * @return List of all SystemRoles
     */
    List<SystemRole> findAllRoles();

    /**
     * @return The SystemRole for administrators
     */
    SystemRole getAdminRole();

    /**
     * @return The SystemRole for registered Users
     */
    SystemRole getUserRole();

    /**
     * @param entity SystemRole to delete
     */
    void delete(SystemRole entity);

    /**
     * @param systemRole SystemRole to save
     */
    void save(SystemRole systemRole);

    /**
     * @param id Id of the SystemRole to retrieve
     * @return The SystemRole if found or null
     */
    SystemRole findById(String id);
}
