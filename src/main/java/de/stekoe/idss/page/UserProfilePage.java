package de.stekoe.idss.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import de.stekoe.idss.model.Systemrole;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Systemrole.USER)
public class UserProfilePage extends LayoutPage {

	public UserProfilePage() {
	}
}
