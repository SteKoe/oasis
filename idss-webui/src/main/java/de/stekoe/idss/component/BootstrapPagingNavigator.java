package de.stekoe.idss.component;


import org.apache.log4j.Logger;
import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.Loop;
import org.apache.wicket.markup.html.list.LoopItem;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationIncrementLink;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigationLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.Model;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class BootstrapPagingNavigator extends Panel {
    private static final Logger LOG = Logger.getLogger(BootstrapPagingNavigator.class);
    private IPageable pageable;

    private boolean showFirstAndLastLink = true;

    public BootstrapPagingNavigator(String id, IPageable pageable) {
        super(id);

        this.pageable = pageable;

        addFirstPageLink();
        addNextPageLink();

        addSinglePagesLinks();

        addPrevPageLink();
        addLastPageLink();
    }

    private void addSinglePagesLinks() {
        add(new Loop("page.list", (int)pageable.getPageCount()) {
            @Override
            protected void populateItem(LoopItem item) {
                final int currentIndex = item.getIndex();
                if(isActivePage(currentIndex)) {
                    item.add(AttributeModifier.append("class", "active"));
                }
                final PagingNavigationLink link = new PagingNavigationLink("page.list.link", pageable, currentIndex);
                item.add(link);
                link.setBody(Model.of(currentIndex + 1));
            }

            private boolean isActivePage(int currentIndex) {
                return currentIndex == pageable.getCurrentPage();
            }
        });
    }

    private void addFirstPageLink() {
        final WebMarkupContainer pageFirst = new WebMarkupContainer("page.first");
        add(pageFirst);

        final PagingNavigationLink pageFirstLink = new PagingNavigationLink("page.first.link", pageable, 0);
        pageFirst.add(pageFirstLink);
        pageFirstLink.setAutoEnable(false);

        if(pageable.getCurrentPage() == 0) {
            pageFirst.add(AttributeModifier.append("class","disabled"));
        }
    }

    private void addPrevPageLink() {
        final WebMarkupContainer pagePrev = new WebMarkupContainer("page.prev");
        add(pagePrev);

        final PagingNavigationIncrementLink pagePrevLink = new PagingNavigationIncrementLink("page.prev.link", pageable, -1);
        pagePrev.add(pagePrevLink);
        pagePrevLink.setAutoEnable(false);

        if(pageable.getCurrentPage() == 0) {
            pagePrev.add(AttributeModifier.append("class", "disabled"));
        }
    }

    private void addNextPageLink() {
        final WebMarkupContainer pageNext = new WebMarkupContainer("page.next");
        add(pageNext);

        final PagingNavigationIncrementLink pageNextLink = new PagingNavigationIncrementLink("page.next.link", pageable, 1);
        pageNext.add(pageNextLink);
        pageNextLink.setAutoEnable(false);

        if(pageable.getCurrentPage() == pageable.getPageCount()-1) {
            pageNext.add(AttributeModifier.append("class", "disabled"));
        }
    }


    private void addLastPageLink() {
        final WebMarkupContainer pageLast = new WebMarkupContainer("page.last");
        add(pageLast);

        final PagingNavigationLink pageLastLink = new PagingNavigationLink("page.last.link", pageable, -1);
        pageLast.add(pageLastLink);
        pageLastLink.setAutoEnable(false);

        if(pageable.getCurrentPage() == pageable.getPageCount()-1) {
            pageLast.add(AttributeModifier.append("class","disabled"));
        }
    }

    public boolean isShowFirstAndLastLink() {
        return showFirstAndLastLink;
    }

    public void setShowFirstAndLastLink(boolean showFirstAndLastLink) {
        this.showFirstAndLastLink = showFirstAndLastLink;
    }
}
