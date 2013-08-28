package de.stekoe.idss.dao;

import java.util.List;

import de.stekoe.idss.model.Project;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ProjectDAO {
    boolean save(Project project);
    List<Project> findByProjectName(String projectName);
}
