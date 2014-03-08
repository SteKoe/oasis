package de.stekoe.idss.service;

import de.stekoe.idss.model.Permission;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface PermissionService {
    /**
     * @param permission Saves the given Permission
     */
    void save(Permission permission);

    /**
     * @param permission Deletes the given Permission
     */
    void delete(Permission permission);
}
