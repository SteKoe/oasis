package de.stekoe.idss.page.auth.user;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import de.stekoe.idss.model.Systemrole;
import de.stekoe.idss.page.LayoutPage;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
@AuthorizeInstantiation(Systemrole.USER)
public class AuthUserPage extends LayoutPage {
}
