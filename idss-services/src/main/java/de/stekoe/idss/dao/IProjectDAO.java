package de.stekoe.idss.dao;

import de.stekoe.idss.model.project.Project;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface IProjectDAO extends IGenericDAO<Project> {

    List<Project> findByProjectName(java.lang.String projectName);

    List<Project> findAllForUser(String user);

    List<Project> findAllForUserPaginated(String userId, int perPage, int curPage);
}
