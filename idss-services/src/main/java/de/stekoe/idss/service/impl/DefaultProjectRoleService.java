package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.Permission;
import de.stekoe.idss.model.ProjectRole;
import de.stekoe.idss.service.ProjectRoleService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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
