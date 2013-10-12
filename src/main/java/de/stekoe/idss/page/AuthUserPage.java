package de.stekoe.idss.page;

import de.stekoe.idss.page.auth.annotation.UserOnly;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
@UserOnly
public class AuthUserPage extends LayoutPage {
    protected AuthUserPage()
    {
        super();
    }

    protected AuthUserPage(final IModel<?> model)
    {
        super(model);
    }

    protected AuthUserPage(final PageParameters parameters)
    {
        super(parameters);
    }
}
