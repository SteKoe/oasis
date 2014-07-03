package de.stekoe.idss.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
@UserOnly
public class AuthUserPage extends ContainerLayoutPage {
    protected AuthUserPage() {
        super();
    }

    protected AuthUserPage(final IModel<?> model) {
        super(model);
    }

    protected AuthUserPage(final PageParameters parameters) {
        super(parameters);
    }
}
