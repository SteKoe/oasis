package de.stekoe.idss.page;

import org.apache.wicket.request.mapper.parameter.PageParameters;

@SuppressWarnings("serial")
public class HomePage extends LayoutPage {

    public HomePage() {
        this(null);
    }

    public HomePage(final PageParameters parameters) {
        super(parameters);
    }
}
