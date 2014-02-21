package de.stekoe.idss.page;

import de.stekoe.idss.page.auth.annotation.AdminOnly;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
@AdminOnly
public class AuthAdminPage extends LayoutPage {
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
