package de.stekoe.idss.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
@AdminOnly
public class AuthAdminPage extends ContainerLayoutPage {
    protected AuthAdminPage() {
        super();
    }

    protected AuthAdminPage(final IModel<?> model) {
        super(model);
    }

    protected AuthAdminPage(final PageParameters parameters) {
        super(parameters);
    }
}
