package de.stekoe.idss.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;

public class HomePage extends LayoutPage {

    /**
     * Construct.
     */
    public HomePage() {
        this(null);
    }

    /**
     * Construct.
     *
     * @param parameters Wrapped page parameters
     */
    public HomePage(final PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
