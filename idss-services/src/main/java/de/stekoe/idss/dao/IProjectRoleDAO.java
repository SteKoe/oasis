package de.stekoe.idss.dao;

import de.stekoe.idss.model.project.ProjectRole;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public interface IProjectRoleDAO extends IGenericDAO<ProjectRole> {
    ProjectRole getRoleByName(String rolename);
}
