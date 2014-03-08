package de.stekoe.idss.service;

import de.stekoe.idss.model.project.ProjectRole;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface ProjectRoleService {
    /**
     * @param id The id of ProjectRole to lookup
     * @return The ProjectRole or null
     */
    ProjectRole findById(String id);

    /**
     * @param role Saves the ProjectRole
     */
    void save(ProjectRole role);

    /**
     * @param entity Deletes the given ProjectRole
     */
    void delete(ProjectRole entity);
}
