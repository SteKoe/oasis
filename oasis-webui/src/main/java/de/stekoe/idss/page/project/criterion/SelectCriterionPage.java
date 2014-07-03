package de.stekoe.idss.page.project.criterion;

import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import de.stekoe.idss.page.project.ProjectPage;

public class SelectCriterionPage extends ProjectPage {
    public SelectCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new BookmarkablePageLink<CreateOrdinalCriterionPage>("link.create.criterion.nominal", CreateNominalCriterionPage.class, getPageParameters()));
        add(new BookmarkablePageLink<CreateOrdinalCriterionPage>("link.create.criterion.ordinal", CreateOrdinalCriterionPage.class, getPageParameters()));

        add(new BookmarkablePageLink<SelectReferenceCriterionPage>("link.select.reference.criterion", SelectReferenceCriterionPage.class, getPageParameters()));

    }
}
