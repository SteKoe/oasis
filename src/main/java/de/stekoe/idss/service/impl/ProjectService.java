package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.User;
import org.springframework.beans.factory.annotation.Autowired;

import de.stekoe.idss.exception.ProjectNotFoundException;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.service.IProjectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
@Transactional
public class ProjectService implements IProjectService {

    @Autowired
    private IProjectDAO projectDAO;

    @Override
    public void create(Project project) {
        projectDAO.save(project);
    }

    @Override
    public void delete(String id) throws ProjectNotFoundException {
    }

    @Override
    public void update(Project project) throws ProjectNotFoundException {
        projectDAO.save(project);
    }

    @Override
    public Project findById(String id) {
        return projectDAO.findById(id);
    }

    @Override
    public List<Project> findAllForUser(User user) {
        return projectDAO.findAllForUser(user);
    }
}
