package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.DownloadLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.AuthAdminPage;
import de.stekoe.idss.service.ReferenceCriterionGroupService;
import de.stekoe.idss.service.ReferenceCriterionService;

public class ReferenceCriterionPage extends AuthAdminPage {

    @Inject
    ReferenceCriterionService referenceCriterionService;

    @Inject
    ReferenceCriterionGroupService referenceCriterionGroupService;

    private static final String ID_LINK = "link";

    public ReferenceCriterionPage() {
        setTitle();
    }

    public ReferenceCriterionPage(IModel<?> model) {
        super(model);
        setTitle();
    }

    public ReferenceCriterionPage(PageParameters parameters) {
        super(parameters);
        setTitle();
    }

    private void setTitle() {
        setTitle(new ResourceModel("label.reference.criterion.overview").getObject());
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

        Link link;

        link = new BookmarkablePageLink<ReferenceCriterionListPage>(ID_LINK, ReferenceCriterionListPage.class);
        link.add(new Label("label", getString("label.reference.criterion.overview")));
        links.add(link);

        link = new BookmarkablePageLink<ReferenceCriterionGroupListPage>(ID_LINK, ReferenceCriterionGroupListPage.class);
        link.add(new Label("label", getString("label.reference.criterion.group.overview")));
        links.add(link);

        link = new BookmarkablePageLink<UploadReferenceCriterionCatalogPage>(ID_LINK, UploadReferenceCriterionCatalogPage.class);
        link.add(new Label("label", getString("label.reference.criterion.import")));
        links.add(link);

        IModel<File> fileModel = new AbstractReadOnlyModel<File>() {
            @Override
            public File getObject() {
                ReferenceCriterionExporter exporter = new ReferenceCriterionExporter();
                exporter.setCriterions(referenceCriterionService.findAll());
                exporter.setCriterionGroups((List<CriterionGroup>) referenceCriterionGroupService.findAll());
                return exporter.createXml();
            }
        };
        link = new DownloadLink(ID_LINK, fileModel, "referenceCriterionCatalog.xml");
        link.add(new Label("label", getString("label.reference.criterion.export")));
        links.add(link);

        return links;
    }
}
