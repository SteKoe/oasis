package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectRoleDAO;
import de.stekoe.idss.model.project.ProjectRole;
import de.stekoe.idss.service.ProjectRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
@Service
@Transactional
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

    @Override
    public void delete(ProjectRole entity) {
        projectRoleDAO.delete(entity);
    }
}
