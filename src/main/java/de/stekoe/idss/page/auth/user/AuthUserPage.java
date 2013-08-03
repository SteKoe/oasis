package de.stekoe.idss.page.auth.user;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import de.stekoe.idss.page.LayoutPage;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
@AuthorizeInstantiation(Roles.USER)
public class AuthUserPage extends LayoutPage {
}
