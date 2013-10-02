package de.stekoe.idss.dao;

import java.util.List;

import de.stekoe.idss.model.Project;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ProjectDAO {
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
}
