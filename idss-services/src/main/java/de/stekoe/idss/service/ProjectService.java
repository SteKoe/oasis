package de.stekoe.idss.service;

import de.stekoe.idss.model.enums.PermissionType;
import de.stekoe.idss.model.project.Project;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ProjectService {

    void delete(java.lang.String id);

    void save(Project project);

    Project findById(java.lang.String id);

    List<Project> findAllForUser(String userId);

    List<Project> findAllForUserPaginated(String userId, int perPage, int curPage);

    boolean isAuthorized(String userId, String projectId, PermissionType permissionType);
}
