package de.stekoe.idss.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.dao.ProjectDAO;
import de.stekoe.idss.exception.ProjectNotFoundException;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.IProjectService;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class ProjectService implements IProjectService {

    @Autowired
    private ProjectDAO projectDAO;

    @Override
    public void create(Project project) {
    }

    @Override
    public void delete(String id) throws ProjectNotFoundException {
    }

    @Override
    public void update(Project project) throws ProjectNotFoundException {
    }

    @Override
    public Project findById(String id) {
        return projectDAO.findById(id);
    }
}
