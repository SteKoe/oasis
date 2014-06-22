/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.page.component.navigation.language;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.OASISWebApplication;
import de.stekoe.idss.session.WebSession;


/**
 * This class provides a language switcher panel.
 */
@SuppressWarnings("serial")
public class LanguageSwitcher extends Panel {

    private final List<Locale> languages = new ArrayList<Locale>();

    /**
     * Construct.
     *
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
                boolean languageEqual = isCurrentLanguage(languageKey);
                final LanguageSwitchLink languageLink = new LanguageSwitchLink("languageLink", languageKey, OASISWebApplication.LANGUAGES.get(languageKey));
                languageLink.setEnabled(!languageEqual);
                item.add(languageLink);
            }

            private boolean isCurrentLanguage(final Locale languageKey) {
                Locale currentLocale = WebSession.get().getLocale();
                boolean languageEqual = languageKey.getLanguage().equals(currentLocale.getLanguage());
                return languageEqual;
            }
        };
        add(loop);
    }

    private List<Locale> getLanguages() {
        List<Locale> languages = new ArrayList<Locale>();

        for (Locale l : OASISWebApplication.LANGUAGES.keySet()) {
            languages.add(l);
        }

        return languages;
    }
}
