package de.stekoe.idss.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import de.stekoe.idss.model.Systemrole;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Systemrole.USER)
public class UserProfilePage extends LayoutPage {

	private static final Logger LOG = Log.getLogger(UserProfilePage.class);

	public UserProfilePage() {
	}
}
