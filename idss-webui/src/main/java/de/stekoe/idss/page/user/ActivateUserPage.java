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

    @SpringBean private UserService itsUserManager;

    private String itsActivationCode = null;

    public ActivateUserPage(PageParameters aParams) {
        super(aParams);

        extractActivationKeyFromPageParameters(aParams);
        if (!itsActivationCode.isEmpty()) {
            User userToActivate = getUserToActivate();
            if (userToActivate != null) {
                activateUser(userToActivate);
            }
        }
    }

    private User getUserToActivate() {
        return itsUserManager.findByActivationCode(itsActivationCode);
    }

    private void extractActivationKeyFromPageParameters(PageParameters aParams) {
        StringValue activationCode = aParams.get(0);
        if (!activationCode.isEmpty()) {
            this.itsActivationCode = activationCode.toString();
        }
    }

    private void activateUser(User aUserToActivate) {
        aUserToActivate.setActivationKey(null);
        aUserToActivate.setUserStatus(UserStatus.ACTIVATED);
        try {
            itsUserManager.save(aUserToActivate);
            setResponsePage(LoginPage.class);
        } catch (UserException e) {
            LOG.error("Error while activating user " + aUserToActivate.getUsername() + "!", e);
        }
    }
}
