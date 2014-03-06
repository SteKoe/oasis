/*
 * Copyright 2014 Stephan Köninger
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
