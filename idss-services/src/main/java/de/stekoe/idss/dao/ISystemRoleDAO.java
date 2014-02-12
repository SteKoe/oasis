package de.stekoe.idss.dao;

import de.stekoe.idss.model.SystemRole;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public interface ISystemRoleDAO extends IGenericDAO<SystemRole> {
    SystemRole getRoleByName(String rolename);
}
