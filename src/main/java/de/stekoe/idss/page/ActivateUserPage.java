package de.stekoe.idss.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.IUserService;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class ActivateUserPage extends LayoutPage {

    @SpringBean
    private IUserService userManager;

    private String activationCode = null;

    /**
     * Construct.
     * @param parameters Wrapped page parameters
     */
    public ActivateUserPage(PageParameters parameters) {
        super(parameters);

        setActivationKey(parameters);
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

    private void setActivationKey(PageParameters parameters) {
        StringValue activationCode = parameters.get(0);
        if (!activationCode.isEmpty()) {
            this.activationCode = activationCode.toString();
        }
    }

    private void activateUser(User userToActivate) {
        userToActivate.setActivationKey(null);
        userManager.save(userToActivate);
    }
}
