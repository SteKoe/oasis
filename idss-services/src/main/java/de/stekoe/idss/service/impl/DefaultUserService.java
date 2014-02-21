package de.stekoe.idss.service.impl;

import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UserException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;
import org.apache.log4j.Logger;
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

    @Inject
    private ISystemRoleDAO systemRoleDAO;

    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public List<User> findAllByUsername(java.lang.String username) {
        return userDAO.findAllByUsername(username);
    }

    @Override
    public User findByEmail(java.lang.String email) {
        return userDAO.findByEmail(email);
    }

    @Override
    public User findByUsernameOrEmail(java.lang.String value) {
        User user = findByUsername(value);
        if(user == null) {
            user = findByEmail(value);
        }

        return user;
    }

    @Override
    public User findById(java.lang.String id) {
        return userDAO.findById(id);
    }

    @Override
    public boolean save(User user) throws UserException {
        if (emailInUse(user)) {
            throw new EmailAddressAlreadyInUseException();
        }
        if (usernameInUse(user)) {
            throw new UsernameAlreadyInUseException();
        }

        userDAO.save(user);
        return true;
    }

    @Override
    public void delete(String user) {
        userDAO.delete(user);
    }

    private boolean usernameInUse(User user) {
        final User userFromDB = userDAO.findByUsername(user.getUsername());
        if(userFromDB == null) {
            return false;
        }
        if(userFromDB != null && userFromDB.getId() == user.getId()) {
            return false;
        }

        return true;
    }

    private boolean emailInUse(User user) {
        final User userFromDB = userDAO.findByEmail(user.getEmail());
        if(userFromDB == null) {
            return false;
        }
        if(userFromDB != null && userFromDB.getId() == user.getId()) {
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
    public List<java.lang.String> getAllEmailAddresses() {
        List<java.lang.String> mail = new ArrayList<java.lang.String>();

        for (User u : getAllUsers()) {
            mail.add(u.getEmail());
        }

        return mail;
    }

    @Override
    public SystemRole getRole(java.lang.String rolename) {
        return systemRoleDAO.getRoleByName(rolename);
    }
}
