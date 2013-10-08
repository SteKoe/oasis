package de.stekoe.idss.page;

import org.apache.log4j.Logger;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class HomePage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(HomePage.class);

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
}
