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

package de.stekoe.idss.wicket;

import de.stekoe.idss.model.L10NEnum;

import org.apache.wicket.Application;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.util.string.Strings;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class EnumChoiceRenderer<T extends Enum<T> & L10NEnum> implements IChoiceRenderer<T> {
    @Override
    public final Object getDisplayValue(T object) {
        final String key = resourceKey(object);
        final String value = Application.get().getResourceSettings().getLocalizer().getString(key, null);
        return postprocess(value);
    }

    protected String resourceKey(T object) {
        return object.getKey();
    }

    protected CharSequence postprocess(String value) {
        return Strings.escapeMarkup(value);
    }

    @Override
    public String getIdValue(T object, int index) {
        return object.name();
    }
}
