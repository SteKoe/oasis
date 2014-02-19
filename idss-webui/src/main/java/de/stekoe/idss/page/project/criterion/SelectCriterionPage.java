package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.model.enums.CriterionType;
import de.stekoe.idss.page.project.ProjectPage;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.request.mapper.parameter.PageParameters;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SelectCriterionPage extends ProjectPage {
    public SelectCriterionPage(PageParameters pageParameters) {
        super(pageParameters);

        add(new BookmarkablePageLink<CreateCriterionPage>("link.create.criterion.nominal", CreateCriterionPage.class, getPageParameters().add("type", CriterionType.NOMINAL)));
        add(new BookmarkablePageLink<CreateCriterionPage>("link.create.criterion.ordinal", CreateCriterionPage.class, getPageParameters().add("type", CriterionType.ORDINAL)));
        add(new BookmarkablePageLink<CreateCriterionPage>("link.create.criterion.metric", CreateCriterionPage.class, getPageParameters().add("type", CriterionType.METRIC)));
    }
}
