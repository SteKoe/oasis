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

package de.stekoe.idss.wicket;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.model.Model;

/**
 * @author Stephan Koeninger 
 */
public class JavascriptEventConfirmation extends AttributeModifier {

    /**
     * @param event Name of JS event like "onClick".
     * @param msg   Message to be shown by JS confirm.
     */
    public JavascriptEventConfirmation(String event, String msg) {
        super(event, new Model<String>(msg));
    }

    @Override
    protected String newValue(final String currentValue, final String replacementValue) {
        String prefix = "var conf = confirm('" + replacementValue + "'); " +
                "if (!conf) return false; ";
        String result = prefix;
        if (currentValue != null) {
            result = prefix + currentValue;
        }
        return result;
    }
}