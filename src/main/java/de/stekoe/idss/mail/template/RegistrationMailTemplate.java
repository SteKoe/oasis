package de.stekoe.idss.mail.template;

import org.apache.wicket.util.template.PackageTextTemplate;

import de.stekoe.idss.IDSSSession;

@SuppressWarnings("serial")
public class RegistrationMailTemplate extends PackageTextTemplate {
    public RegistrationMailTemplate() {
        super(RegistrationMailTemplate.class, RegistrationMailTemplate.class.getSimpleName()+".txt");
        setLocale(IDSSSession.get().getLocale());
    }
}
