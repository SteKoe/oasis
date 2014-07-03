package de.stekoe.idss.page.component.modal.button;

import de.agilecoders.wicket.core.markup.html.bootstrap.button.ButtonBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.button.Buttons;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;

/**
 * @author Stephan Koeninger 
 */
public class ModalSaveButton extends Link {

    public ModalSaveButton(String id) {
        this(id, null);
    }


    public ModalSaveButton(String id, IModel<String> model) {
        super(id, model);
    }

    @Override
    public void onClick() {
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        setBody(getDefaultModel());
        add(new ButtonBehavior(Buttons.Type.Success));
    }
}
