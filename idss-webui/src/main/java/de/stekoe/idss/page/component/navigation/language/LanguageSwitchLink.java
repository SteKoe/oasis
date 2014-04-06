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
