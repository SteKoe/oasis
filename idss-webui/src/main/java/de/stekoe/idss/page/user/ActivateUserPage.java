package de.stekoe.idss.page.user;

import de.stekoe.idss.exception.UserException;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.enums.UserStatus;
import de.stekoe.idss.page.LayoutPage;
import de.stekoe.idss.page.auth.LoginPage;
import de.stekoe.idss.service.UserService;
import org.apache.log4j.Logger;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class ActivateUserPage extends LayoutPage {
    private static final Logger LOG = Logger.getLogger(ActivateUserPage.class);

    @SpringBean
    private UserService userManager;

    private java.lang.String activationCode = null;

    /**
     * Construct.
     * @param parameters Wrapped page parameters
     */
    public ActivateUserPage(PageParameters parameters) {
        super(parameters);

        extractActivationKeyFromPageParameters(parameters);
        if (!activationCode.isEmpty()) {
            User userToActivate = getUserToActivate();
            if (userToActivate != null) {
                activateUser(userToActivate);
            }
        }
    }

    private User getUserToActivate() {
        User userToActivate = userManager.findByActivationCode(activationCode);
        return userToActivate;
    }

    private void extractActivationKeyFromPageParameters(PageParameters parameters) {
        StringValue activationCode = parameters.get(0);
        if (!activationCode.isEmpty()) {
            this.activationCode = activationCode.toString();
        }
    }

    private void activateUser(User userToActivate) {
        userToActivate.setActivationKey(null);
        userToActivate.setUserStatus(UserStatus.ACTIVATED);
        try {
            userManager.save(userToActivate);
            throw new RestartResponseException(LoginPage.class);
        } catch (UserException e) {
            LOG.error("Error while activating user " + userToActivate.getUsername() + "!");
        }
    }
}