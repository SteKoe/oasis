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

package de.stekoe.idss.page;

import java.util.Locale;

import org.apache.log4j.Logger;

/**
 * @author Stephan Koeninger
 */
@SuppressWarnings("serial")
public class LogoutPage extends LayoutPage {

    private static final Logger LOG = Logger.getLogger(LogoutPage.class);

    /**
     * Construct.
     */
    public LogoutPage() {
        if (getSession().getUser() != null) {
            LOG.info("User " + getSession().getUser().getUsername() + " is about to log out!");
        }

        Locale locale = getSession().getLocale();
        getSession().invalidate();
        getSession().success(getString("message.logout.success"));
        getSession().setLocale(locale);
        setResponsePage(HomePage.class);
    }
}
