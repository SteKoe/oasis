package de.stekoe.idss.page;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.Systemrole;

@SuppressWarnings("serial")
@AuthorizeInstantiation(Systemrole.USER)
public class AuthUserPage extends LayoutPage {
	public AuthUserPage() {
		super();
	}

	public AuthUserPage(IModel<?> model) {
		super(model);
	}

	public AuthUserPage(PageParameters parameters) {
		super(parameters);
	}
}
