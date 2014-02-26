package de.stekoe.idss.page.project.criterion.page.element;

import de.stekoe.idss.model.criterion.CriterionPage;
import de.stekoe.idss.model.criterion.SingleScaledCriterion;
import de.stekoe.idss.page.project.criterion.EditOrdinalCriterionPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class SingleScaledCriterionElement extends Panel {
    private final CompoundPropertyModel<SingleScaledCriterion> itsModel;
    private SingleScaledCriterion itsSingleScaledCriterion;

    public SingleScaledCriterionElement(String aId, SingleScaledCriterion aSingleScaledCriterion) {
        super(aId);
        itsModel = new CompoundPropertyModel<SingleScaledCriterion>(aSingleScaledCriterion);
        setDefaultModel(new CompoundPropertyModel<SingleScaledCriterion>(aSingleScaledCriterion));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        SingleScaledCriterion aSingleScaledCriterion = (SingleScaledCriterion) getDefaultModelObject();

        add(new Label("name"));
        add(new BookmarkablePageLink<EditOrdinalCriterionPage>("edit", EditOrdinalCriterionPage.class, new PageParameters(getPage().getPageParameters()).add("criterionId", aSingleScaledCriterion.getId())));
    }
}
