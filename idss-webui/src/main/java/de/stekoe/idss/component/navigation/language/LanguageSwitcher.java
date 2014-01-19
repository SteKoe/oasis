package de.stekoe.idss.component.navigation.language;

import de.stekoe.idss.WebApplication;
import de.stekoe.idss.session.WebSession;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


/**
 * This class provides a language switcher panel.
 */
@SuppressWarnings("serial")
public class LanguageSwitcher extends Panel {

    private final List<Locale> languages = new ArrayList<Locale>();

    /**
     * Construct.
     * @param id the wicket:id
     */
    public LanguageSwitcher(String id) {
        super(id);

        if (getLanguages().size() <= 1) {
            setVisible(false);
        } else {
            createLanguageSwitcher();
        }
    }

    private void createLanguageSwitcher() {
        ListView<Locale> loop = new ListView<Locale>("languages", getLanguages()) {



            @Override
            protected void populateItem(final ListItem<Locale> item) {
                final Locale languageKey = item.getModelObject();
                Locale currentLocale = WebSession.get().getLocale();

                boolean languageEqual = languageKey.getLanguage().equals(currentLocale.getLanguage());
                final LanguageSwitchLink languageLink = new LanguageSwitchLink("languageLink", languageKey);
                languageLink.setEnabled(!languageEqual);

                item.add(languageLink);
            }
        };
        add(loop);
    }

    private List<Locale> getLanguages() {
        List<Locale> languages = new ArrayList<Locale>();

        for (Locale l : WebApplication.LANGUAGES) {
            languages.add(l);
        }

        return languages;
    }
}
