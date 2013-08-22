package de.stekoe.idss.mail.template;

import java.util.Map;

import org.apache.wicket.util.template.PackageTextTemplate;

import de.stekoe.idss.IDSSSession;

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
        setLocale(IDSSSession.get().getLocale());
    }

    /**
     * Convenience method which delegates to {@code PackageTextTemplate#asString()}.
     * @param variables A {@code Map} with containing the contents.
     * @return the string with replaces variables
     */
    public String setVariables(Map<String, ?> variables) {
        return super.asString(variables);
    }
}
