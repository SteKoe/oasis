package de.stekoe.idss.service;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.enums.PermissionType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
public interface ProjectService {

    void delete(java.lang.String id);

    void save(Project project);

    Project findById(java.lang.String id);

    List<Project> findAllForUser(String userId);

    List<Project> findAllForUserPaginated(String userId, int perPage, int curPage);

    boolean isAuthorized(String userId, String projectId, PermissionType permissionType);
}
