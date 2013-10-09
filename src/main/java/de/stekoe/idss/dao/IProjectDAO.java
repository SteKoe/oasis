package de.stekoe.idss.dao;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectDAO {
    /**
     * @param project The project object to persist.
     * @return True on success, false otherwise.
     */
    boolean save(Project project);

    /**
     * @param projectName The project name to lookup
     * @return A list of projects with the given name.
     */
    List<Project> findByProjectName(String projectName);

    /**
     * @param id The id of a project
     * @return The project identified by id.
     */
    Project findById(String id);

    /**
     * @return List of Projects
     */
    List<Project> findAllForUser(User user);
}
