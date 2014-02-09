package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.ProjectRoleService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class DefaultProjectRoleService implements ProjectRoleService {

    @Autowired
    IProjectRoleDAO projectRoleDAO;

    @Override
    public ProjectRole findById(String id) {
        return projectRoleDAO.findById(id);
    }

    @Override
    public void save(ProjectRole role) {
        projectRoleDAO.save(role);
    }
}
