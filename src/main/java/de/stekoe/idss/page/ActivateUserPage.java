package de.stekoe.idss.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.StringValue;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserManager;

@SuppressWarnings("serial")
public class ActivateUserPage extends LayoutPage {

	@SpringBean
	private UserManager userManager;

	private String activationCode = null;
	
	private static final Logger LOG = Log.getLogger(ActivateUserPage.class);
	
	public ActivateUserPage(PageParameters parameters) {
		super(parameters);
		
		setActivationKey(parameters);
		if(!activationCode.isEmpty()) {
			User userToActivate = getUserToActivate();
			if(userToActivate != null) {
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
		if(!activationCode.isEmpty()) {
			this.activationCode  = activationCode.toString();
		}
	}

	private void activateUser(User userToActivate) {
		userToActivate.setActivationKey(null);
		userManager.update(userToActivate);
	}
}
