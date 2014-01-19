package de.stekoe.idss;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */

import org.apache.wicket.Session;
import org.apache.wicket.request.Request;
import org.apache.wicket.request.Response;

public class FakeWebApplication extends WebApplication {
    @Override
    public Session newSession(Request request, Response response) {
        return new FakeWebSession(request);
    }
}