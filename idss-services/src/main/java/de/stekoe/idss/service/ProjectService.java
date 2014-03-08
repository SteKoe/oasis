package de.stekoe.idss.service;

import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ProjectService {

    /**
     * @param id The id of a Project to delete
     */
    void delete(String id);

    /**
     * @param project A project to save
     */
    void save(Project project);

    /**
     * @param id The id of a project to find
     * @return The project with the given id or null
     */
    Project findById(String id);

    /**
     * @param userId Id of a User to retrieve all projects he/she is involved
     * @return A list of Project
     */
    List<Project> findAllForUser(String userId);

    /**
     * @param userId Id of a User to retrieve all projects he/she is involved
     * @param perPage The number of entries per page
     * @param curPage The current page number
     * @return A list of Projects
     */
    List<Project> findAllForUserPaginated(String userId, int perPage, int curPage);

    /**
     * @param userId The id of a User to check permissions for
     * @param projectId The id of a Project to check permissions on
     * @param permissionType The PermissionType to be checked
     * @return true if a User is authorized to perform a given action, false otherwise
     */
    boolean isAuthorized(String userId, String projectId, PermissionType permissionType);
}
