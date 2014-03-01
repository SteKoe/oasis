package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UserException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
@Transactional
public class DefaultUserService implements UserService {

    @Inject
    private IUserDAO userDAO;

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public List<User> findAllByUsername(String username) {
        return userDAO.findAllByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User findByUsernameOrEmail(String value) {
        User user = findByUsername(value);
        if (user == null) {
            user = findByEmail(value);
        }

        return user;
    }

    @Override
    public User findById(String id) {
        return userDAO.findById(id);
    }

    @Override
    public void save(User entity) throws UserException {
        if (emailInUse(entity)) {
            throw new EmailAddressAlreadyInUseException();
        } else if (usernameInUse(entity)) {
            throw new UsernameAlreadyInUseException();
        } else {
            userDAO.save(entity);
        }
    }

    @Override
    public void delete(User entity) {
        userDAO.delete(entity);
    }

    private boolean usernameInUse(User user) {
        final User userFromDB = userDAO.findByUsername(user.getUsername());
        if (userFromDB == null) {
            return false;
        } else if (userFromDB.getId().equals(user.getId())) {
            return false;
        }

        return true;
    }

    private boolean emailInUse(User user) {
        final User userFromDB = userDAO.findByEmail(user.getEmail());
        if (userFromDB == null) {
            return false;
        } else if (userFromDB.getId().equals(user.getId())) {
            return false;
        }

        return true;
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.findAll();
    }

    @Override
    public User findByActivationCode(String code) {
        return userDAO.findByActivationCode(code);
    }

    @Override
    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<String>();
        for (User u : getAllUsers()) {
            usernames.add(u.getUsername());
        }
        return usernames;
    }

    @Override
    public List<String> getAllEmailAddresses() {
        List<String> mail = new ArrayList<String>();

        for (User u : getAllUsers()) {
            mail.add(u.getEmail());
        }

        return mail;
    }
}
