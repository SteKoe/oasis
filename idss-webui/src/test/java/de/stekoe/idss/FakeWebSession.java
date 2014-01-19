package de.stekoe.idss;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.AuthService;
import de.stekoe.idss.service.AuthStatus;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.request.Request;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class FakeWebSession extends WebSession {

    @Inject AuthService authService;

    private Map<String, User> users = new HashMap<String, User>();

    public FakeWebSession(Request request) {
        super(request);

        final User userAuthUser = TestFactory.createAuthUser();
        users.put(userAuthUser.getUsername(), userAuthUser);

        final User adminAuthUser = TestFactory.createAuthAdminUser();
        users.put(adminAuthUser.getUsername(), adminAuthUser);
    }

    @Override
    protected AuthStatus authenticateByService(java.lang.String username, java.lang.String password) {
        if(!users.containsKey(username)) {
            return AuthStatus.USER_NOT_FOUND;
        }

        User user = users.get(username);

        if(!authService.checkPassword(password, user.getPassword())) {
            return AuthStatus.WRONG_PASSWORD;
        }

        return AuthStatus.SUCCESS;
    }

    @Override
    protected User getUserFromService(java.lang.String username) {
        return TestFactory.createAuthUser();
    }
}
