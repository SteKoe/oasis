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

package de.stekoe.idss.mail.template;

import java.util.Map;

import org.apache.wicket.util.template.PackageTextTemplate;

import de.stekoe.idss.session.WebSession;

/**
 * Template for the registration mail.
 */
@SuppressWarnings("serial")
public class ResetPasswordMailTemplate extends PackageTextTemplate {

    /**
     * Construct.
     */
    public ResetPasswordMailTemplate() {
        super(ResetPasswordMailTemplate.class, ResetPasswordMailTemplate.class.getSimpleName() + ".txt");
        setLocale(WebSession.get().getLocale());
    }

    /**
     * Convenience method which delegates to {@code PackageTextTemplate#asString()}.
     *
     * @param variables A {@code Map} with containing the contents.
     * @return the string with replaces variables
     */
    public String setVariables(Map<String, ?> variables) {
        return super.asString(variables);
    }
}
