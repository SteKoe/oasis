package de.stekoe.idss.page;

import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * Extends the layout of LayoutPage and wraps the content in a container with same width as header and footer.
 * |----- window width -----|
 * |                        |
 * |     |-- header --|     |
 * |                        |
 * |     |- content --|     |
 * |                        |
 * |     |-- footer --|     |
 */
public abstract class ContainerLayoutPage extends LayoutPage {
    public ContainerLayoutPage() {
        super();
    }

    public ContainerLayoutPage(IModel<?> model) {
        super(model);
    }

    public ContainerLayoutPage(PageParameters parameters) {
        super(parameters);
    }
}
