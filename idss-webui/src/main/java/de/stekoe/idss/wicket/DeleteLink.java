package de.stekoe.idss.wicket;

import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;

public abstract class DeleteLink extends Link {
    public DeleteLink(String id) {
        super(id);

        add(new AttributeAppender("class", "btn btn-danger"));
        setBody(Model.of(getString("label.delete")));
    }
}
