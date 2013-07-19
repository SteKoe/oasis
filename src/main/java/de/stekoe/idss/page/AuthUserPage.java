package de.stekoe.idss.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import de.stekoe.idss.model.Systemrole;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
@AuthorizeInstantiation(Systemrole.USER)
public class AuthUserPage extends LayoutPage {
}
