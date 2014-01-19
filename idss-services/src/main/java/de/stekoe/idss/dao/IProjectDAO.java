package de.stekoe.idss.dao;

import de.stekoe.idss.model.Project;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public interface IProjectDAO extends IGenericDAO<Project> {

    List<Project> findByProjectName(java.lang.String projectName);

    List<Project> findAllForUser(String user);

    List<Project> findAllForUserPaginated(String userId, int perPage, int curPage);
}
