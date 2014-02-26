package de.stekoe.idss.mail.template;

import de.stekoe.idss.session.WebSession;
import org.apache.wicket.util.template.PackageTextTemplate;

import java.util.Map;

/**
 * Template for the registration mail.
 */
@SuppressWarnings("serial")
public class RegistrationMailTemplate extends PackageTextTemplate {

    /**
     * Construct.
     */
    public RegistrationMailTemplate() {
        super(RegistrationMailTemplate.class, RegistrationMailTemplate.class.getSimpleName() + ".txt");
        setLocale(WebSession.get().getLocale());
    }

    /**
     * Convenience method which delegates to {@code PackageTextTemplate#asString()}.
     *
     * @param variables A {@code Map} with containing the contents.
     * @return the string with replaces variables
     */
    public String setVariables(Map<String, ?> variables) {
        return super.asString(variables);
    }
}
