package de.stekoe.idss;

import de.stekoe.idss.page.LayoutPage;

/**
 * Custom 404 HTTP Status class
 */
public class Error404Page extends LayoutPage {
    /**
     * Construct.
     */
    public Error404Page() {
        super();
    }

    @Override
    public boolean isVersioned() {
        return false;
    }

    @Override
    public boolean isErrorPage() {
        return true;
    }
}