package de.stekoe.idss.service;

import de.stekoe.idss.model.project.ProjectRole;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public interface ProjectRoleService {
    ProjectRole findById(String id);

    void save(ProjectRole role);

    void delete(ProjectRole entity);
}
