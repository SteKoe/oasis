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

package de.stekoe.idss.page.component.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.model.StringResourceModel;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
@SuppressWarnings("serial")
public class Placeholder extends Behavior {

    private String placeholder;

    /**
     * @param placeholder The string which is set as value of the placeholder attribute in HTML.
     */
    public Placeholder(String placeholder) {
        this(placeholder, null);
    }

    /**
     * @param placeholder The string which is set as value of the placeholder attribute in HTML.
     * @param component   Component which is used to load the properties file from.
     */
    public Placeholder(String placeholder, Component component) {
        if (component == null) {
            this.placeholder = placeholder;
        } else {
            this.placeholder = new StringResourceModel(placeholder, component,
                    null).getString();
        }
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        tag.put("placeholder", this.placeholder);
    }
}
