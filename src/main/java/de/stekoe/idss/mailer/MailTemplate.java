package de.stekoe.idss.mailer;

import org.apache.wicket.util.template.PackageTextTemplate;

import com.google.common.base.Charsets;

import de.stekoe.idss.IDSSSession;

/**
 * This class provides basic functionality for mail templates.
 */
@SuppressWarnings("serial")
public class MailTemplate extends PackageTextTemplate {

    /**
     * Contruct.
     *
     * @param clazz The <code>Class</code> to be used for retrieving the classloader for loading the <code>PackagedTextTemplate</code>
     * @param templateFile Filename of the template.
     */
    public MailTemplate(Class<?> clazz, String templateFile) {
        super(clazz, templateFile);
        setCharset(Charsets.UTF_8);
        setLocale(IDSSSession.get().getLocale());
    }
}
