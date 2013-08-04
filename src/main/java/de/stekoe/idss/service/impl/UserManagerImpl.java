package de.stekoe.idss.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.IDSSSession;
import de.stekoe.idss.dao.RoleDAO;
import de.stekoe.idss.dao.UserDAO;
import de.stekoe.idss.exception.UserAlreadyExistsException;
import de.stekoe.idss.model.Role;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserManager;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
public class UserManagerImpl implements UserManager {

    private static final Logger LOG = Logger.getLogger(UserManagerImpl.class);

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RoleDAO roleDAO;


    @Override
    @Transactional
    public boolean insertUser(User user) throws UserAlreadyExistsException {
        User existentUser = userDAO.findByUsername(user.getUsername());
        if (existentUser == null) {
            userDAO.insert(user);
            return true;
        } else {
            LOG.warn("Tried to insert new user with existing username!");
            throw new UserAlreadyExistsException();
        }
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    @Transactional
    public void update(User entity) {
        userDAO.update(entity);
    }

    /**
     * Method to login a user by username/email and password.
     */
    @Override
    @Transactional
    public LoginStatus login(String username, String password) {
        User user = userDAO.findByUsername(username);

        // User not found by username, try email instead
        if(user == null) {
            user = userDAO.findByEmail(username);
        }

        // User definitely not found.
        if(user == null)
            return LoginStatus.USER_NOT_FOUND;

        // User is not activated
        if(user.getActivationKey() != null)
            return LoginStatus.USER_NOT_ACTIVATED;

        // Check password
        if(!BCrypt.checkpw(password, user.getPassword())) {
            return LoginStatus.WRONG_PASSWORD;
        } else {
            IDSSSession.get().setUser(user);
            return LoginStatus.SUCCESS;
        }
    }

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public User findByActivationCode(String code) {
        return userDAO.findByActivationCode(code);
    }

    @Override
    @Transactional
    public List<String> getAllUsernames() {
        List<String> usernames = new ArrayList<String>();
        for (User u : getAllUsers()) {
            usernames.add(u.getUsername());
        }
        return usernames;
    }

    @Override
    @Transactional
    public List<String> getAllEmailAddresses() {
        List<String> mail = new ArrayList<String>();

        for (User u : getAllUsers()) {
            mail.add(u.getEmail());
        }

        return mail;
    }

    @Override
    public Role getRole(String rolename) {
        return roleDAO.getRoleByName(rolename);
    }
}
