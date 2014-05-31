package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.page.AuthAdminPage;

public class ReferenceCriterionPage extends AuthAdminPage {

    private static final String ID_LINK = "link";

    public ReferenceCriterionPage() {
    }

    public ReferenceCriterionPage(IModel<?> model) {
        super(model);
    }

    public ReferenceCriterionPage(PageParameters parameters) {
        super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        add(new ListView<Link>("links", getLinks()) {
            @Override
            protected void populateItem(ListItem<Link> item) {
                item.add(item.getModelObject());
            }
        });
    }

    private List<Link> getLinks() {
        List<Link> links = new ArrayList<Link>();

        BookmarkablePageLink<?> link = new BookmarkablePageLink<ReferenceCriterionListPage>(ID_LINK, ReferenceCriterionListPage.class);
        link.add(new Label("label", getString("label.reference.criterion.overview")));
        links.add(link);

        link = new BookmarkablePageLink<ReferenceCriterionGroupListPage>(ID_LINK, ReferenceCriterionGroupListPage.class);
        link.add(new Label("label", getString("label.reference.criterion.group.overview")));
        links.add(link);

        link = new BookmarkablePageLink<UploadReferenceCriterionCatalogPage>(ID_LINK, UploadReferenceCriterionCatalogPage.class);
        link.add(new Label("label", getString("label.reference.criterion.upload")));
        links.add(link);

        return links;
    }
}
