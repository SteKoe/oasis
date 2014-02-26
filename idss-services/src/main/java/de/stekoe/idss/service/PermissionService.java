package de.stekoe.idss.service;

import de.stekoe.idss.model.Permission;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface PermissionService {
    void save(Permission permission);

    void delete(Permission permission);
}
