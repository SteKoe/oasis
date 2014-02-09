package de.stekoe.idss.service;

import de.stekoe.idss.model.project.ProjectRole;
import org.springframework.stereotype.Service;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
public interface ProjectRoleService {
    ProjectRole findById(String id);
    void save(ProjectRole role);
}
