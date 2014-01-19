package de.stekoe.idss.dao;

import de.stekoe.idss.model.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Repository
public interface IUserDAO extends IGenericDAO<User> {

    User findByUsername(String username);

    User findByActivationCode(String code);

    User findByEmail(String email);

    List<User> findAllByUsername(String username);
}
