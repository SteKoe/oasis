package de.stekoe.idss.dao;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectDAO extends IGenericDAO<Project> {

    /**
     * @param projectName The project name to lookup
     * @return A list of projects with the given name.
     */
    List<Project> findByProjectName(String projectName);

    /**
     * @return List of Projects
     */
    List<Project> findAllForUser(User user);
}
