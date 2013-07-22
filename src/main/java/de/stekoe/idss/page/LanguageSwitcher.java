package de.stekoe.idss.page;

import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;


@SuppressWarnings("serial")
public class LanguageSwitcher extends Panel {

    private final Map<String, Locale> languages = new HashMap<String, Locale>();

    public LanguageSwitcher(String id) {
        super(id);

        setLanguages();

        ListView loop = new ListView("languages", getLanguages().) {

            @Override
            protected void populateItem(ListItem item) {
                // TODO Auto-generated method stub

            }
        };

        add(loop);
    }

    private void setLanguages() {
        languages.put("en", Locale.ENGLISH);
        languages.put("de", Locale.GERMAN);
    }

    private Map<String, Locale> getLanguages() {
        return languages;
    }

}
