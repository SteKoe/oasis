package de.stekoe.idss.page.auth.admin;

import org.apache.wicket.authroles.authorization.strategies.role.Roles;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import de.stekoe.idss.page.LayoutPage;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
@AuthorizeInstantiation(Roles.ADMIN)
public class AuthAdminPage extends LayoutPage {
}
