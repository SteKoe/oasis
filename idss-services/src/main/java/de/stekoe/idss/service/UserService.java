package de.stekoe.idss.service;

import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UserException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stekoe.de>
 */
public interface UserService {
    User findByUsername(String username);

    List<User> findAllByUsername(String username);

    User findByEmail(String email);

    User findByUsernameOrEmail(String value);

    User findById(String id);

    List<User> getAllUsers();

    User findByActivationCode(String code);

    List<String> getAllUsernames();

    List<String> getAllEmailAddresses();

    SystemRole getRole(String rolename);

    boolean save(User user) throws UserException;

    void delete(String userId);
}
