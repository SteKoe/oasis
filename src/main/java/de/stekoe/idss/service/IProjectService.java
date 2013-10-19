package de.stekoe.idss.service;

import de.stekoe.idss.exception.ProjectNotFoundException;
import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectService {
    /**
     * @param project The project to save.
     */
    void create(Project project);

    /**
     * @param id Id of the project to be deleted.
     * @throws ProjectNotFoundException Thrown if project to delete does not exist.
     */
    void delete(String id);

    /**
     * @param project The project to update
     * @throws ProjectNotFoundException Thrown if the project to update does not exist.
     */
    void update(Project project);

    /**
     * @param id The id of the project to load
     * @return Project with given id.
     */
    Project findById(String id);

    /**
     * Reads all projects from database which are related to a user.
     * @return A list with Projects
     */
    List<Project> findAllForUser(User user);

    /**
     * Checks wether a user is allowed to perform an action on project.
     *
     * @param user
     * @param project
     * @return true if access granted, false otherwise
     */
    boolean isAuthorized(User user, Project project);
}
