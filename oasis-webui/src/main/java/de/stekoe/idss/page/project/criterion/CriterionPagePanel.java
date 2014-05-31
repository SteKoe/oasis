package de.stekoe.idss.page.project.criterion;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.panel.Panel;

import de.stekoe.idss.model.CriterionGroup;
import de.stekoe.idss.model.CriterionPage;
import de.stekoe.idss.model.NominalScaledCriterion;
import de.stekoe.idss.model.OrdinalScaledCriterion;
import de.stekoe.idss.model.PageElement;
import de.stekoe.idss.repository.UserChoiceRepository;


public class CriterionPagePanel extends Panel {

    @Inject
    UserChoiceRepository userChoiceRepository;

    private static final Logger LOG = Logger.getLogger(CriterionPagePanel.class);

    private final List<PageElement> pageElements;
    private final List<PageElementPanel> pageElementPanels = new ArrayList<PageElementPanel>();

    private final List<CriterionPage> criterionPages;

    public CriterionPagePanel(final String wicketId, final List<CriterionPage> criterionPages, final CriterionPage criterionPage) {
        super(wicketId);
        this.criterionPages = criterionPages;
        this.pageElements = criterionPage.getPageElements();

        ListView<PageElement> listView = new SurveyPageElementsListView("elements", this.pageElements);
        listView.setReuseItems(true);
        add(listView);
    }

    private final class SurveyPageElementsListView extends ListView<PageElement> {
        private SurveyPageElementsListView(String id, List<? extends PageElement> list) {
            super(id, list);
        }

        @Override
        protected void populateItem(ListItem<PageElement> item) {
            PageElement pageElement = item.getModelObject();

            WebMarkupContainer element = new WebMarkupContainer("element");
            if(pageElement instanceof NominalScaledCriterion) {
                NominalScaledCriterion nsc = (NominalScaledCriterion) pageElement;
                if(!nsc.isMultipleChoice()) {
                    element = new NominalScaledCriterionSingleChoicePanel("element", nsc);
                } else {
                    element = new NominalScaledCriterionMultipleChoicePanel("element", nsc);
                }
            } else if(pageElement instanceof OrdinalScaledCriterion) {
                element = new OrdinalScaledCriterionSingleChoicePanel("element", (OrdinalScaledCriterion) pageElement);
            } else if(pageElement instanceof CriterionGroup) {
                element = new CriterionGroupSurveyElementPanel("element", (CriterionGroup) pageElement);
                element.add(new SurveyPageElementsListView("elements", ((CriterionGroup) pageElement).getCriterions()));
            }

            if(element instanceof PageElementPanel) {
                pageElementPanels.add((PageElementPanel) element);
            }
            item.add(element);
        }
    }

    public List<PageElementPanel> getPageElementPanels() {
        return pageElementPanels;
    }

}
