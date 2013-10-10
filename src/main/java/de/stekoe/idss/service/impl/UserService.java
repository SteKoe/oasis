package de.stekoe.idss.service.impl;

import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.dao.ISystemRoleDAO;
import de.stekoe.idss.dao.IUserDAO;
import de.stekoe.idss.exception.EmailAddressAlreadyInUseException;
import de.stekoe.idss.exception.UsernameAlreadyInUseException;
import de.stekoe.idss.model.SystemRole;
import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService;
import org.apache.log4j.Logger;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@Service
@Transactional
public class UserService implements IUserService {

    private static final Logger LOG = Logger.getLogger(UserService.class);

    @Autowired
    private IUserDAO userDAO;

    @Autowired
    private ISystemRoleDAO systemRoleDAO;


    @Override
    public User findByUsername(String username) {
        return userDAO.findByUsername(username);
    }

    @Override
    public boolean save(User user) throws EmailAddressAlreadyInUseException, UsernameAlreadyInUseException {
        if (user.getId() == null) {
            if (emailInUse(user.getEmail())) {
                throw new EmailAddressAlreadyInUseException();
            }
            if (usernameInUse(user.getUsername())) {
                throw new UsernameAlreadyInUseException();
            }
        }

        return userDAO.save(user);
    }

    private boolean usernameInUse(String username) {
        return userDAO.findByUsername(username) != null;
    }

    private boolean emailInUse(String email) {
        return userDAO.findByEmail(email) != null;
    }

    /**
     * Method to login a user by username/email and password.
     * @param username The username
     * @param password The password
     * @return The status of login indicated by {@code LoginStatus}
     */
    @Override
    public LoginStatus login(String username, String password) {
        User user = userDAO.findByUsername(username);

        // User not found by username, try email instead
        if (user == null) {
            user = userDAO.findByEmail(username);
        }

        // User definitely not found.
        if (user == null) {
            return LoginStatus.USER_NOT_FOUND;
        }

        // User is not activated
        if (user.getActivationKey() != null) {
            return LoginStatus.USER_NOT_ACTIVATED;
        }

        // Check password
        if (!BCrypt.checkpw(password, user.getPassword())) {
            return LoginStatus.WRONG_PASSWORD;
        } else {
            WebSession.get().setUser(user);
            return LoginStatus.SUCCESS;
        }
    }

    @Override
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
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

    @Override
    public SystemRole getRole(String rolename) {
        return systemRoleDAO.getRoleByName(rolename);
    }
}
