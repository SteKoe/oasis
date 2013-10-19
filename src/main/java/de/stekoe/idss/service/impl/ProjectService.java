package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IProjectDAO;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IProjectService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void delete(String id) {
        projectDAO.delete(id);
    }

    @Override
    public void update(Project project) {
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

    @Override
    public boolean isAuthorized(User user, Project project) {
        if (project.getProjectTeam().contains(user) == false) {
            return false;
        }

        return true;
    }
}
