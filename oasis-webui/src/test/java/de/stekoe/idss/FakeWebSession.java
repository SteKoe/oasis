package de.stekoe.idss;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.request.Request;

import de.stekoe.idss.model.User;
import de.stekoe.idss.repository.SystemRoleRepository;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;
import de.stekoe.idss.session.WebSession;

public class FakeWebSession extends WebSession {

    private static final Logger LOG = Logger.getLogger(FakeWebSession.class);

    @Inject
    AuthService authService;

    @Inject
    UserService userService;

    @Inject
    SystemRoleRepository systemRoleRepository;

    private final Map<String, User> users = new HashMap<String, User>();

    public FakeWebSession(Request request) {
        super(request);


        final User userAuthUser = TestFactory.createAuthUser();
        users.put(userAuthUser.getUsername(), userAuthUser);

        final User adminAuthUser = TestFactory.createAuthAdminUser();
        users.put(adminAuthUser.getUsername(), adminAuthUser);

        try {
            systemRoleRepository.save(userAuthUser.getRoles());
            systemRoleRepository.save(adminAuthUser.getRoles());

            userService.save(userAuthUser);
            userService.save(adminAuthUser);
        } catch (UserException e) {
            LOG.error("Error saving a user", e);
        }
    }

    @Override
    protected AuthStatus authenticateByService(java.lang.String username, java.lang.String password) {
        if (!users.containsKey(username)) {
            return AuthStatus.ERROR;
        }

        User user = users.get(username);

        if (!authService.checkPassword(password, user.getPassword())) {
            return AuthStatus.ERROR;
        }

        return AuthStatus.SUCCESS;
    }

    @Override
    protected User getUserFromService(java.lang.String username) {
        return TestFactory.createAuthUser();
    }
}
