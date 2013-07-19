package de.stekoe.idss.component.behavior;

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

    public Placeholder(String placeholder) {
        this(placeholder, null);
    }

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
