package de.stekoe.idss.component;


import de.agilecoders.wicket.core.util.Attributes;
import org.apache.log4j.Logger;
import org.apache.wicket.Component;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.model.Model;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class BootstrapPagingNavigator extends PagingNavigator {

    private static final Logger LOG = Logger.getLogger(BootstrapPagingNavigator.class);

    public BootstrapPagingNavigator(String id, IPageable pageable) {
        this(id, pageable, null);
    }

    public BootstrapPagingNavigator(String id, IPageable pageable, IPagingLabelProvider labelProvider) {
        super(id, pageable, labelProvider);

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final Component first = get("first").getParent();
        LOG.error(first);
        first.add(new AttributeAppender("class", new Model("disabled"), " "));
    }

    @Override
    protected void onComponentTag(ComponentTag tag) {
        super.onComponentTag(tag);

        Attributes.addClass(tag, "pagination");
    }
}
