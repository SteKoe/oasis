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

package de.stekoe.idss.page.component;


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

public class BootstrapPagingNavigator extends Panel {
    private static final Logger LOG = Logger.getLogger(BootstrapPagingNavigator.class);
    private final IPageable pageable;

    private boolean showFirstAndLastLink = true;

    public BootstrapPagingNavigator(String id, IPageable pageable) {
        super(id);

        this.pageable = pageable;

        addFirstPageLink();
        addPrevPageLink();

        addSinglePagesLinks();

        addNextPageLink();
        addLastPageLink();
    }

    private void addSinglePagesLinks() {
        add(new Loop("page.list", (int) pageable.getPageCount()) {
            @Override
            protected void populateItem(LoopItem item) {
                final int currentIndex = item.getIndex();
                if (isActivePage(currentIndex)) {
                    item.add(AttributeModifier.append("class", "active"));
                }
                final PagingNavigationLink link = new PagingNavigationLink("page.list.link", pageable, currentIndex);
                item.add(link);
                link.add(new AttributeModifier("data-page", Model.of(link.getPageNumber())));
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
        pageFirstLink.add(new AttributeModifier("data-page", Model.of("0")));
        pageFirst.add(AttributeModifier.append("class", new Model<String>() {
            @Override
            public String getObject() {
                if (pageable.getCurrentPage() == 0) {
                    return "disabled";
                } else {
                    return "enabled";
                }
            }
        }));

    }

    private void addPrevPageLink() {
        final WebMarkupContainer pagePrev = new WebMarkupContainer("page.prev");
        add(pagePrev);

        final PagingNavigationIncrementLink pagePrevLink = new PagingNavigationIncrementLink("page.prev.link", pageable, -1);
        pagePrev.add(pagePrevLink);
        pagePrevLink.setAutoEnable(false);
        pagePrevLink.add(new AttributeModifier("data-page", Model.of(pagePrevLink.getPageNumber())));
        pagePrev.add(AttributeModifier.append("class", new Model<String>() {
            @Override
            public String getObject() {
                if (pageable.getCurrentPage() == 0) {
                    return "disabled";
                } else {
                    return "enabled";
                }
            }
        }));
    }

    private void addNextPageLink() {
        final WebMarkupContainer pageNext = new WebMarkupContainer("page.next");
        add(pageNext);

        final PagingNavigationIncrementLink pageNextLink = new PagingNavigationIncrementLink("page.next.link", pageable, 1);
        pageNext.add(pageNextLink);
        pageNextLink.setAutoEnable(false);
        pageNextLink.add(new AttributeModifier("data-page", Model.of(pageNextLink.getPageNumber())));
        pageNext.add(AttributeModifier.append("class", new Model<String>() {
            @Override
            public String getObject() {
                if (pageable.getCurrentPage() == pageable.getPageCount() - 1) {
                    return "disabled";
                } else {
                    return "enabled";
                }
            }
        }));

    }


    private void addLastPageLink() {
        final WebMarkupContainer pageLast = new WebMarkupContainer("page.last");
        add(pageLast);

        final PagingNavigationLink pageLastLink = new PagingNavigationLink("page.last.link", pageable, -1);
        pageLast.add(pageLastLink);
        pageLastLink.setAutoEnable(false);
        pageLastLink.add(new AttributeModifier("data-page", Model.of(pageLastLink.getPageNumber())));
        pageLast.add(AttributeModifier.append("class", new Model<String>(){
            @Override
            public String getObject() {
                if (pageable.getCurrentPage() == pageable.getPageCount() - 1) {
                    return "disabled";
                } else {
                    return "enabled";
                }
            }
        }));
    }

    public boolean isShowFirstAndLastLink() {
        return showFirstAndLastLink;
    }

    public void setShowFirstAndLastLink(boolean showFirstAndLastLink) {
        this.showFirstAndLastLink = showFirstAndLastLink;
    }
}
