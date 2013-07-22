package de.stekoe.idss.page.error;

import javax.servlet.http.HttpServletResponse;

import org.apache.wicket.request.http.WebResponse;

import de.stekoe.idss.page.LayoutPage;

/**
 * Custom 404 HTTP Status class
 */
public class Error404Page extends LayoutPage {

    private static final long serialVersionUID = 1L;

    /**
     * Construct.
     */
    public Error404Page() {
    }

    @Override
    protected void configureResponse(final WebResponse response) {
        super.configureResponse(response);
        HttpServletResponse servletResp = (HttpServletResponse) response.getContainerResponse();
        servletResp.setStatus(HttpServletResponse.SC_NOT_FOUND);
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