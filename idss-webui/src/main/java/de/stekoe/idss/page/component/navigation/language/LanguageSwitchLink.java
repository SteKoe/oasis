package de.stekoe.idss.page.component.navigation.language;

import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

import java.util.Locale;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class LanguageSwitchLink extends Link {

    private final Locale lang;

    public LanguageSwitchLink(String id, Locale lang) {
        super(id);
        this.lang = lang;

        setBody(Model.of(getLang().getDisplayName(WebSession.get().getLocale())));
    }

    @Override
    public void onClick() {
        WebSession.get().setLocale(getLang());
        setResponsePage(getPage().getClass(), getPage().getPageParameters());
    }

    public Locale getLang() {
        return lang;
    }
}
