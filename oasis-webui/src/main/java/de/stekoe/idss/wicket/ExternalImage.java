package de.stekoe.idss.wicket;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * Allows to use external URLs for image.
 *
 * @author Stephan Koeninger 
 */
public class ExternalImage extends WebMarkupContainer {

    public ExternalImage(String id, String url) {
        this(id, new Model<String>(url));
    }

    public ExternalImage(String id, IModel<String> model) {
        super(id, model);
        add(AttributeAppender.replace("src", model));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);
        checkComponentTag(tag, "img");
    }
}
