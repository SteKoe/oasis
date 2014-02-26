package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.page.project.ProjectPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SelectCriterionPage extends ProjectPage {
    public SelectCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new BookmarkablePageLink<CreateOrdinalCriterionPage>("link.create.criterion.nominal", CreateOrdinalCriterionPage.class, getPageParameters()));
        add(new BookmarkablePageLink<CreateOrdinalCriterionPage>("link.create.criterion.ordinal", CreateOrdinalCriterionPage.class, getPageParameters()));
        add(new BookmarkablePageLink<CreateOrdinalCriterionPage>("link.create.criterion.metric", CreateOrdinalCriterionPage.class, getPageParameters()));
    }
}
