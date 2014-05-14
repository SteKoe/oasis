package de.stekoe.idss.page.project.criterion.referencecatalog;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.page.PaginationConfigurator;
import de.stekoe.idss.page.component.DataListView;
import de.stekoe.idss.wicket.DeleteLink;
import de.stekoe.idss.wicket.JavascriptEventConfirmation;

public class ReferenceCriterionGroupListPage extends ReferenceCriterionPage {

    @Inject
    ReferenceCriterionGroupDataProvider referenceCriterionGroupDataProvider;

    @Inject
    PaginationConfigurator paginationConfigurator;

    public ReferenceCriterionGroupListPage() {
        add(new BookmarkablePageLink<CreateReferenceCriterionGroupPage>("link.add", CreateReferenceCriterionGroupPage.class));


        DataListView<CriterionGroup> dataListView = new DataListView<CriterionGroup>("list", referenceCriterionGroupDataProvider, paginationConfigurator.getValueFor(ReferenceCriterionGroupListPage.class)) {
            @Override
            protected List<? extends Link> getButtons(final CriterionGroup modelObject) {
                List<Link> links = new ArrayList<Link>();

                DeleteLink deleteLink = new DeleteLink(DataListView.BUTTON_ID) {
                    @Override
                    public void onClick() {
                        setResponsePage(getPage());
                    }
                };
                deleteLink.add(new AttributeAppender("class", " btn-xs"));
                deleteLink.add(new JavascriptEventConfirmation("onClick", String.format(getString("project.delete.confirm"), modelObject.getName())));

                PageParameters pageDetailsParameters = new PageParameters();
                pageDetailsParameters.add("criterionGroupId", modelObject.getId());

                BookmarkablePageLink<EditReferenceCriterionGroupPage> editCriterionLink = new BookmarkablePageLink<EditReferenceCriterionGroupPage>(DataListView.BUTTON_ID, EditReferenceCriterionGroupPage.class, pageDetailsParameters);
                editCriterionLink.setBody(Model.of(getString("label.edit")));
                editCriterionLink.add(new AttributeModifier("class", "btn btn-default btn-xs"));
                links.add(editCriterionLink);

                return links;
            }
        };
        add(dataListView);
    }
}
