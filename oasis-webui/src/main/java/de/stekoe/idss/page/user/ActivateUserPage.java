package de.stekoe.idss.page.user;

import org.apache.log4j.Logger;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.page.HomePage;
import de.stekoe.idss.page.LayoutPage;
import de.stekoe.idss.page.LoginPage;
import de.stekoe.idss.service.UserException;
import de.stekoe.idss.service.UserService;

/**
 * @author Stephan Koeninger
 */
@SuppressWarnings("serial")
public class ActivateUserPage extends LayoutPage {
    private static final Logger LOG = Logger.getLogger(ActivateUserPage.class);

    @SpringBean
    private UserService itsUserManager;

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

        WebSession.get().error(getString("message.user.notfound"));
        setResponsePage(HomePage.class);
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
            WebSession.get().success(getString("message.user.activation.success"));
            setResponsePage(LoginPage.class);
        } catch (UserException e) {
            LOG.error("Error while activating user " + aUserToActivate.getUsername() + "!", e);
            WebSession.get().error(getString("message.user.activation.error"));
            setResponsePage(HomePage.class);
        }
    }
}
