package de.stekoe.idss.dao;

import de.stekoe.idss.model.UserProfile;
import org.springframework.stereotype.Repository;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public interface IUserProfileDAO extends IGenericDAO<UserProfile> {
}
