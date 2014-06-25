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

import de.stekoe.idss.model.Criterion;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.page.PaginationConfigurator;
import de.stekoe.idss.page.component.DataListView;
import de.stekoe.idss.service.ReferenceCriterionService;
import de.stekoe.idss.session.WebSession;
import de.stekoe.idss.wicket.DeleteLink;
import de.stekoe.idss.wicket.JavascriptEventConfirmation;

public class ReferenceCriterionListPage extends ReferenceCriterionPage {

    @Inject
    ReferenceCriterionDataProvider referenceCriterionDataProvider;

    @Inject
    PaginationConfigurator paginationConfigurator;

    @Inject
    ReferenceCriterionService referenceCriterionService;

    public ReferenceCriterionListPage() {
        add(new BookmarkablePageLink<CreateNominalReferenceCriterionPage>("link.add", CreateNominalReferenceCriterionPage.class));


        DataListView<Criterion> dataListView = new DataListView<Criterion>("referencecriteriongroup.list", referenceCriterionDataProvider, paginationConfigurator.getValueFor(ReferenceCriterionListPage.class)) {
            @Override
            protected List<? extends Link> getButtons(final Criterion modelObject) {
                List<Link> links = new ArrayList<Link>();

                PageParameters pageDetailsParameters = new PageParameters();
                pageDetailsParameters.add("criterionId", modelObject.getId());

                if(modelObject instanceof NominalScaledCriterion) {
                    BookmarkablePageLink<EditNominalReferenceCriterionPage> editCriterionLink = new BookmarkablePageLink<EditNominalReferenceCriterionPage>(DataListView.BUTTON_ID, EditNominalReferenceCriterionPage.class, pageDetailsParameters);
                    editCriterionLink.setBody(Model.of(getString("label.edit")));
                    editCriterionLink.add(new AttributeModifier("class", "btn btn-default btn-xs"));
                    links.add(editCriterionLink);
                } else if (modelObject instanceof OrdinalScaledCriterion) {
                    BookmarkablePageLink<EditOrdinalReferenceCriterionPage> editCriterionLink = new BookmarkablePageLink<EditOrdinalReferenceCriterionPage>(DataListView.BUTTON_ID, EditOrdinalReferenceCriterionPage.class, pageDetailsParameters);
                    editCriterionLink.setBody(Model.of(getString("label.edit")));
                    editCriterionLink.add(new AttributeModifier("class", "btn btn-default btn-xs"));
                    links.add(editCriterionLink);
                }

                DeleteLink deleteLink = new DeleteLink(DataListView.BUTTON_ID) {
                    @Override
                    public void onClick() {
                        referenceCriterionService.delete(modelObject.getId());
                        WebSession.get().success(getString("message.delete.success"));
                        setResponsePage(getPage());
                    }
                };
                deleteLink.add(new AttributeAppender("class", " btn-xs"));
                deleteLink.add(new JavascriptEventConfirmation("onClick", String.format(getString("message.delete.confirm"), modelObject.getName())));
                links.add(deleteLink);

                return links;
            }
        };
        add(dataListView);
    }
}
