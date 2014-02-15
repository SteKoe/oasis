package de.stekoe.idss.component;


import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.link.AbstractLink;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.IPagingLabelProvider;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;

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
    protected AbstractLink newPagingNavigationLink(String id, IPageable pageable, int pageNumber) {
        final PagingNavigationLink link = (PagingNavigationLink) super.newPagingNavigationLink(id, pageable, pageNumber);
        final long currentPage = pageable.getCurrentPage();
        return link;
    }
}
